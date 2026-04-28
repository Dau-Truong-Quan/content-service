package garden.quan.content_service.offering;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * The frontend speaks of these as "services" — the public URL preserves
 * that vocabulary even though the entity is named {@code Offering}.
 */
@RestController
@RequestMapping("/api/services")
public class OfferingController {

    private final OfferingService service;

    public OfferingController(OfferingService service) {
        this.service = service;
    }

    @GetMapping
    public List<OfferingDto> list(@RequestParam(required = false) String category) {
        return service.list(category);
    }

    @GetMapping("/{id}")
    public OfferingDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OfferingDto> create(@Valid @RequestBody OfferingRequest req,
                                              UriComponentsBuilder uri) {
        OfferingDto created = service.create(req);
        URI location = uri.path("/api/services/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OfferingDto update(@PathVariable UUID id, @Valid @RequestBody OfferingRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
