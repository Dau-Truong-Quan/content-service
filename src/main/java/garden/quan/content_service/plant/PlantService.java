package garden.quan.content_service.plant;

import garden.quan.content_service.common.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static garden.quan.content_service.plant.PlantRepository.*;

@Service
@Transactional
public class PlantService {

    private final PlantRepository repo;

    public PlantService(PlantRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "plants-list", key = "#category + '|' + #search + '|' + #tag")
    @Transactional(readOnly = true)
    public List<PlantDto> list(String category, String search, String tag) {
        Specification<Plant> spec = Specification.where(hasCategory(category))
            .and(matchesSearch(search))
            .and(hasTag(tag));
        return repo.findAll(spec, Sort.by("commonNameEn")).stream()
            .map(PlantDto::from)
            .toList();
    }

    @Cacheable(value = "plants", key = "#id")
    @Transactional(readOnly = true)
    public PlantDto get(UUID id) {
        return repo.findById(id).map(PlantDto::from)
            .orElseThrow(() -> new NotFoundException("Plant", id));
    }

    @Caching(evict = {
        @CacheEvict(value = "plants-list", allEntries = true),
        @CacheEvict(value = "stats", allEntries = true)
    })
    public PlantDto create(PlantRequest req) {
        return PlantDto.from(repo.save(req.toEntity()));
    }

    @Caching(evict = {
        @CacheEvict(value = "plants", key = "#id"),
        @CacheEvict(value = "plants-list", allEntries = true),
        @CacheEvict(value = "stats", allEntries = true)
    })
    public PlantDto update(UUID id, PlantRequest req) {
        Plant existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException("Plant", id));
        req.applyTo(existing);
        return PlantDto.from(repo.save(existing));
    }

    @Caching(evict = {
        @CacheEvict(value = "plants", key = "#id"),
        @CacheEvict(value = "plants-list", allEntries = true),
        @CacheEvict(value = "stats", allEntries = true)
    })
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new NotFoundException("Plant", id);
        repo.deleteById(id);
    }
}
