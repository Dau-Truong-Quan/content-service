package garden.quan.content_service.offering;

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
public class OfferingService {

    private final OfferingRepository repo;

    public OfferingService(OfferingRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "offerings-list", key = "#category")
    @Transactional(readOnly = true)
    public List<OfferingDto> list(String category) {
        return repo.search(category).stream().map(OfferingDto::from).toList();
    }

    @Cacheable(value = "offerings", key = "#id")
    @Transactional(readOnly = true)
    public OfferingDto get(UUID id) {
        return repo.findById(id).map(OfferingDto::from)
            .orElseThrow(() -> new NotFoundException("Offering", id));
    }

    @Caching(evict = {
        @CacheEvict(value = "offerings-list", allEntries = true)
    })
    public OfferingDto create(OfferingRequest req) {
        return OfferingDto.from(repo.save(req.toEntity()));
    }

    @Caching(evict = {
        @CacheEvict(value = "offerings", key = "#id"),
        @CacheEvict(value = "offerings-list", allEntries = true)
    })
    public OfferingDto update(UUID id, OfferingRequest req) {
        Offering existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException("Offering", id));
        req.applyTo(existing);
        return OfferingDto.from(repo.save(existing));
    }

    @Caching(evict = {
        @CacheEvict(value = "offerings", key = "#id"),
        @CacheEvict(value = "offerings-list", allEntries = true)
    })
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new NotFoundException("Offering", id);
        repo.deleteById(id);
    }
}
