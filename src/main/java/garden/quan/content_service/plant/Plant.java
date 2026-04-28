package garden.quan.content_service.plant;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Plant {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "common_name_en", nullable = false)
    private String commonNameEn;

    @Column(name = "common_name_vi")
    private String commonNameVi;

    @Column(name = "latin_name", nullable = false)
    private String latinName;

    /** flowering | foliage | edible | herb | tree | climber | succulent */
    @Column(nullable = false, length = 32)
    private String category;

    @Column(nullable = false)
    private String family;

    /** full-sun | partial-shade | shade */
    @Column(nullable = false, length = 32)
    private String light;

    /** low | moderate | high */
    @Column(nullable = false, length = 32)
    private String water;

    /** easy | moderate | challenging */
    @Column(nullable = false, length = 32)
    private String difficulty;

    @ElementCollection
    @CollectionTable(name = "plant_bloom_seasons", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "season")
    @Builder.Default
    private List<String> bloomSeason = new ArrayList<>();

    @Column(name = "height_en", nullable = false)
    private String heightEn;

    @Column(name = "height_vi")
    private String heightVi;

    @Column(name = "notes_en", columnDefinition = "TEXT")
    private String notesEn;

    @Column(name = "notes_vi", columnDefinition = "TEXT")
    private String notesVi;

    @Column(name = "story_en", columnDefinition = "TEXT")
    private String storyEn;

    @Column(name = "story_vi", columnDefinition = "TEXT")
    private String storyVi;

    @Column(name = "planted_on")
    private LocalDate plantedOn;

    /** Hex color used as the card accent on the frontend. */
    @Column(nullable = false, length = 16)
    private String color;

    /** Key into the frontend's botanical-illustration component. */
    @Column(nullable = false, length = 64)
    private String illustration;

    @ElementCollection
    @CollectionTable(name = "plant_tags", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

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
