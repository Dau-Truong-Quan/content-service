package garden.quan.content_service.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * On startup, when {@code app.jwt.dev-print-token=true}, logs a freshly-signed
 * admin JWT that you can paste into curl/Postman to test admin endpoints.
 * Disabled in prod via {@code application-prod.yml}.
 */
@Component
@Slf4j
public class DevTokenPrinter implements CommandLineRunner {

    private final JwtProperties props;

    public DevTokenPrinter(JwtProperties props) {
        this.props = props;
    }

    @Override
    public void run(String... args) {
        if (!props.devPrintToken()) {
            return;
        }
        SecretKey key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
        Instant now = Instant.now();
        String token = Jwts.builder()
            .issuer(props.issuer())
            .subject("dev-admin")
            .claim("roles", List.of("ADMIN"))
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(Duration.ofDays(7))))
            .signWith(key)
            .compact();

        log.info("");
        log.info("╔══════════════════════════════════════════════════════════════════════╗");
        log.info("║ DEV ADMIN JWT (valid 7 days):                                        ║");
        log.info("╚══════════════════════════════════════════════════════════════════════╝");
        log.info("Authorization: Bearer {}", token);
        log.info("");
        log.info("Try: curl -H 'Authorization: Bearer {}' \\", token);
        log.info("       http://localhost:8082/api/plants");
        log.info("");
    }
}
