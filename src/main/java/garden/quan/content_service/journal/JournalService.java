package garden.quan.content_service.journal;

import garden.quan.content_service.common.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class JournalService {

    private final JournalRepository repo;

    public JournalService(JournalRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "journal-list", key = "#season")
    @Transactional(readOnly = true)
    public List<JournalEntryDto> list(String season) {
        return repo.search(season).stream().map(JournalEntryDto::from).toList();
    }

    @Cacheable(value = "journal", key = "#id")
    @Transactional(readOnly = true)
    public JournalEntryDto get(UUID id) {
        return repo.findById(id).map(JournalEntryDto::from)
            .orElseThrow(() -> new NotFoundException("JournalEntry", id));
    }

    @Caching(evict = {
        @CacheEvict(value = "journal-list", allEntries = true),
        @CacheEvict(value = "stats", allEntries = true)
    })
    public JournalEntryDto create(JournalEntryRequest req) {
        return JournalEntryDto.from(repo.save(req.toEntity()));
    }

    @Caching(evict = {
        @CacheEvict(value = "journal", key = "#id"),
        @CacheEvict(value = "journal-list", allEntries = true),
        @CacheEvict(value = "stats", allEntries = true)
    })
    public JournalEntryDto update(UUID id, JournalEntryRequest req) {
        JournalEntry existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException("JournalEntry", id));
        req.applyTo(existing);
        return JournalEntryDto.from(repo.save(existing));
    }

    @Caching(evict = {
        @CacheEvict(value = "journal", key = "#id"),
        @CacheEvict(value = "journal-list", allEntries = true),
        @CacheEvict(value = "stats", allEntries = true)
    })
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new NotFoundException("JournalEntry", id);
        repo.deleteById(id);
    }
}
