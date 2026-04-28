package garden.quan.content_service.gallery;

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
@RequestMapping("/api/gallery")
public class GalleryController {

    private final GalleryService service;

    public GalleryController(GalleryService service) {
        this.service = service;
    }

    @GetMapping
    public List<GalleryImageDto> list(@RequestParam(required = false) String season) {
        return service.list(season);
    }

    @GetMapping("/{id}")
    public GalleryImageDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GalleryImageDto> create(@Valid @RequestBody GalleryImageRequest req,
                                                  UriComponentsBuilder uri) {
        GalleryImageDto created = service.create(req);
        URI location = uri.path("/api/gallery/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GalleryImageDto update(@PathVariable UUID id, @Valid @RequestBody GalleryImageRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
