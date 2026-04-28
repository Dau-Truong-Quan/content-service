package garden.quan.content_service.offering;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "offering_highlights")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OfferingHighlight {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    @JsonIgnore
    private Offering offering;

    @Column(nullable = false)
    private int position;

    @Column(name = "text_en", nullable = false)
    private String textEn;

    @Column(name = "text_vi")
    private String textVi;
}
