package garden.quan.content_service.gallery;

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
public class GalleryService {

    private final GalleryRepository repo;

    public GalleryService(GalleryRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "gallery-list", key = "#season")
    @Transactional(readOnly = true)
    public List<GalleryImageDto> list(String season) {
        return repo.search(season).stream().map(GalleryImageDto::from).toList();
    }

    @Cacheable(value = "gallery", key = "#id")
    @Transactional(readOnly = true)
    public GalleryImageDto get(UUID id) {
        return repo.findById(id).map(GalleryImageDto::from)
            .orElseThrow(() -> new NotFoundException("GalleryImage", id));
    }

    @Caching(evict = {
        @CacheEvict(value = "gallery-list", allEntries = true)
    })
    public GalleryImageDto create(GalleryImageRequest req) {
        return GalleryImageDto.from(repo.save(req.toEntity()));
    }

    @Caching(evict = {
        @CacheEvict(value = "gallery", key = "#id"),
        @CacheEvict(value = "gallery-list", allEntries = true)
    })
    public GalleryImageDto update(UUID id, GalleryImageRequest req) {
        GalleryImage existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException("GalleryImage", id));
        req.applyTo(existing);
        return GalleryImageDto.from(repo.save(existing));
    }

    @Caching(evict = {
        @CacheEvict(value = "gallery", key = "#id"),
        @CacheEvict(value = "gallery-list", allEntries = true)
    })
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new NotFoundException("GalleryImage", id);
        repo.deleteById(id);
    }
}
