package garden.quan.content_service.offering;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OfferingDto(
    UUID id,
    String title,
    String titleVi,
    String category,
    String tagline,
    String taglineVi,
    String description,
    String descriptionVi,
    BigDecimal priceFrom,
    String currency,
    String duration,
    String durationVi,
    String serves,
    String servesVi,
    String illustration,
    String color,
    List<String> highlights,
    List<String> highlightsVi
) {
    public static OfferingDto from(Offering o) {
        List<String> en = o.getHighlights().stream().map(OfferingHighlight::getTextEn).toList();
        List<String> vi = o.getHighlights().stream().map(OfferingHighlight::getTextVi).toList();
        // If every VI is null, drop the field entirely so it serialises as omitted.
        boolean anyVi = vi.stream().anyMatch(s -> s != null && !s.isBlank());
        return new OfferingDto(
            o.getId(),
            o.getTitleEn(), o.getTitleVi(),
            o.getCategory(),
            o.getTaglineEn(), o.getTaglineVi(),
            o.getDescriptionEn(), o.getDescriptionVi(),
            o.getPriceFrom(), o.getCurrency(),
            o.getDurationEn(), o.getDurationVi(),
            o.getServesEn(), o.getServesVi(),
            o.getIllustration(), o.getColor(),
            en,
            anyVi ? vi : null
        );
    }
}
