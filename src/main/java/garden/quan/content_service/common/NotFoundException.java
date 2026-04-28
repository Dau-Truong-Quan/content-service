package garden.quan.content_service.common;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Object id) {
        super("%s not found: %s".formatted(entity, id));
    }
}
