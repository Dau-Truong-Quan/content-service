package garden.quan.content_service.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT validation config. content-service does not issue tokens —
 * identity-service does. We just verify the Bearer token's signature
 * and check for the admin role.
 */
@ConfigurationProperties("app.jwt")
public record JwtProperties(
    String secret,
    String issuer,
    boolean devPrintToken
) {}
