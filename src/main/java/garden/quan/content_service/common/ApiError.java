package garden.quan.content_service.common;

import java.time.Instant;
import java.util.List;

/** Uniform error response payload. */
public record ApiError(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path,
    List<FieldViolation> violations
) {
    public ApiError(int status, String error, String message, String path) {
        this(Instant.now(), status, error, message, path, null);
    }

    public ApiError(int status, String error, String message, String path, List<FieldViolation> violations) {
        this(Instant.now(), status, error, message, path, violations);
    }

    public record FieldViolation(String field, String message) {}
}
