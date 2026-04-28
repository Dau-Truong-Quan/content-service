package garden.quan.content_service.plant;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService service;

    public PlantController(PlantService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlantDto> list(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String tag
    ) {
        return service.list(category, search, tag);
    }

    @GetMapping("/{id}")
    public PlantDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlantDto> create(@Valid @RequestBody PlantRequest req,
                                           UriComponentsBuilder uri) {
        PlantDto created = service.create(req);
        URI location = uri.path("/api/plants/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PlantDto update(@PathVariable UUID id, @Valid @RequestBody PlantRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
