package garden.quan.content_service.stats;

import garden.quan.content_service.gallery.GalleryRepository;
import garden.quan.content_service.journal.JournalRepository;
import garden.quan.content_service.offering.OfferingRepository;
import garden.quan.content_service.plant.Plant;
import garden.quan.content_service.plant.PlantRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * Aggregate counts for the frontend's dashboard / about page.
 * Mirrors the shape returned by the original {@code GardenDataService.getStats()}.
 */
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final PlantRepository plants;
    private final JournalRepository journal;
    private final GalleryRepository gallery;
    private final OfferingRepository offerings;
    private final int yearsTended;

    public StatsController(PlantRepository plants,
                           JournalRepository journal,
                           GalleryRepository gallery,
                           OfferingRepository offerings,
                           @Value("${app.stats.years-tended:8}") int yearsTended) {
        this.plants = plants;
        this.journal = journal;
        this.gallery = gallery;
        this.offerings = offerings;
        this.yearsTended = yearsTended;
    }

    @GetMapping
    @Cacheable("stats")
    @Transactional(readOnly = true)
    public StatsDto get() {
        long plantCount = plants.count();
        Set<String> families = new HashSet<>();
        for (Plant p : plants.findAll()) families.add(p.getFamily());
        return new StatsDto(
            plantCount,
            families.size(),
            journal.count(),
            gallery.count(),
            offerings.count(),
            yearsTended
        );
    }

    public record StatsDto(
        long plantCount,
        int familyCount,
        long journalEntries,
        long galleryImages,
        long offerings,
        int yearsTended
    ) {}
}
