package garden.quan.content_service.journal;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "journal_entries")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JournalEntry {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private LocalDate date;

    /** spring | summer | autumn | winter */
    @Column(nullable = false, length = 16)
    private String season;

    @Column(name = "title_en", nullable = false)
    private String titleEn;

    @Column(name = "title_vi")
    private String titleVi;

    @Column(name = "excerpt_en", columnDefinition = "TEXT")
    private String excerptEn;

    @Column(name = "excerpt_vi", columnDefinition = "TEXT")
    private String excerptVi;

    @Column(name = "body_en", columnDefinition = "TEXT", nullable = false)
    private String bodyEn;

    @Column(name = "body_vi", columnDefinition = "TEXT")
    private String bodyVi;

    @Column(name = "weather_en")
    private String weatherEn;

    @Column(name = "weather_vi")
    private String weatherVi;

    private String mood;

    @Column(name = "read_minutes", nullable = false)
    private int readMinutes;

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
