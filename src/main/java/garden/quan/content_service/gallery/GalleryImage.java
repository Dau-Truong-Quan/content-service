package garden.quan.content_service.gallery;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "gallery_images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GalleryImage {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "caption_en", nullable = false)
    private String captionEn;

    @Column(name = "caption_vi")
    private String captionVi;

    /** spring | summer | autumn | winter */
    @Column(nullable = false, length = 16)
    private String season;

    /** Stored as {@code image_year} because {@code year} is a reserved keyword in PostgreSQL. */
    @Column(name = "image_year", nullable = false)
    private int year;

    /** portrait | landscape | square */
    @Column(nullable = false, length = 16)
    private String ratio;

    /** Frontend uses a 2-stop linear gradient as a placeholder. */
    @Column(name = "palette_from", nullable = false, length = 16)
    private String paletteFrom;

    @Column(name = "palette_to", nullable = false, length = 16)
    private String paletteTo;

    @Column(nullable = false, length = 64)
    private String illustration;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

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
