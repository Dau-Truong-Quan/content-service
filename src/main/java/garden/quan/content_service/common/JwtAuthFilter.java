package garden.quan.content_service.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validates Bearer JWTs issued by identity-service. Extracts roles from
 * the {@code roles} claim and populates the SecurityContext.
 *
 * <p>Public endpoints work without a token; admin endpoints rely on
 * {@code @PreAuthorize("hasRole('ADMIN')")} or URL-level rules in
 * {@link SecurityConfig}.
 */
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final SecretKey key;
    private final String issuer;

    public JwtAuthFilter(JwtProperties props) {
        byte[] secretBytes = props.secret().getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length < 32) {
            throw new IllegalStateException(
                "app.jwt.secret must be at least 32 bytes for HS256");
        }
        this.key = Keys.hmacShaKeyFor(secretBytes);
        this.issuer = props.issuer();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .requireIssuer(issuer)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

                String subject = claims.getSubject();
                Collection<SimpleGrantedAuthority> authorities = extractAuthorities(claims);

                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(subject, null, authorities);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException ex) {
                log.debug("Rejecting invalid JWT: {}", ex.getMessage());
                // leave context empty — public endpoints still work, admin ones return 401/403
            }
        }
        chain.doFilter(req, res);
    }

    private Collection<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        Object roles = claims.get("roles");
        List<String> roleList;
        if (roles instanceof List<?> list) {
            roleList = list.stream().map(Object::toString).toList();
        } else if (roles instanceof String s && !s.isBlank()) {
            roleList = List.of(s.split("\\s+"));
        } else {
            roleList = List.of();
        }
        return roleList.stream()
            .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r.toUpperCase())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }
}
