package garden.quan.content_service.offering;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The "Service" domain from the frontend (cooking, delivery, workshop, consulting).
 * Renamed to {@code Offering} in the backend to avoid colliding with Spring's
 * {@code @Service} stereotype in code reviews and IDE search.
 */
@Entity
@Table(name = "offerings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Offering {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "title_en", nullable = false)
    private String titleEn;

    @Column(name = "title_vi")
    private String titleVi;

    /** cooking | delivery | workshop | consulting */
    @Column(nullable = false, length = 32)
    private String category;

    @Column(name = "tagline_en", nullable = false)
    private String taglineEn;

    @Column(name = "tagline_vi")
    private String taglineVi;

    @Column(name = "description_en", columnDefinition = "TEXT", nullable = false)
    private String descriptionEn;

    @Column(name = "description_vi", columnDefinition = "TEXT")
    private String descriptionVi;

    @Column(name = "price_from", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceFrom;

    @Column(nullable = false, length = 8)
    private String currency;

    @Column(name = "duration_en", nullable = false)
    private String durationEn;

    @Column(name = "duration_vi")
    private String durationVi;

    @Column(name = "serves_en")
    private String servesEn;

    @Column(name = "serves_vi")
    private String servesVi;

    @Column(nullable = false, length = 64)
    private String illustration;

    @Column(nullable = false, length = 16)
    private String color;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("position ASC")
    @Builder.Default
    private List<OfferingHighlight> highlights = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

    public void replaceHighlights(List<OfferingHighlight> next) {
        this.highlights.clear();
        for (OfferingHighlight h : next) {
            h.setOffering(this);
            this.highlights.add(h);
        }
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
