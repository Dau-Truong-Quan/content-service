package garden.quan.content_service.journal;

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
@RequestMapping("/api/journal")
public class JournalController {

    private final JournalService service;

    public JournalController(JournalService service) {
        this.service = service;
    }

    @GetMapping
    public List<JournalEntryDto> list(@RequestParam(required = false) String season) {
        return service.list(season);
    }

    @GetMapping("/{id}")
    public JournalEntryDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JournalEntryDto> create(@Valid @RequestBody JournalEntryRequest req,
                                                  UriComponentsBuilder uri) {
        JournalEntryDto created = service.create(req);
        URI location = uri.path("/api/journal/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public JournalEntryDto update(@PathVariable UUID id, @Valid @RequestBody JournalEntryRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
