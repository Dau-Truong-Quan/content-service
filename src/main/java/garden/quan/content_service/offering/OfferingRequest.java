package garden.quan.content_service.offering;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record OfferingRequest(
    @NotBlank String title,
    String titleVi,
    @NotBlank @Pattern(regexp = "cooking|delivery|workshop|consulting") String category,
    @NotBlank String tagline,
    String taglineVi,
    @NotBlank String description,
    String descriptionVi,
    @NotNull @DecimalMin("0.00") BigDecimal priceFrom,
    @NotBlank @Size(max = 8) String currency,
    @NotBlank String duration,
    String durationVi,
    String serves,
    String servesVi,
    @NotBlank String illustration,
    @NotBlank @Pattern(regexp = "#[0-9a-fA-F]{6}") String color,
    @NotNull @Size(min = 1) List<@NotBlank String> highlights,
    /** Optional VI parallel array. If supplied, must match length of {@code highlights}. */
    List<String> highlightsVi
) {
    public Offering toEntity() {
        Offering o = Offering.builder()
            .titleEn(title).titleVi(titleVi)
            .category(category)
            .taglineEn(tagline).taglineVi(taglineVi)
            .descriptionEn(description).descriptionVi(descriptionVi)
            .priceFrom(priceFrom).currency(currency)
            .durationEn(duration).durationVi(durationVi)
            .servesEn(serves).servesVi(servesVi)
            .illustration(illustration).color(color)
            .highlights(new ArrayList<>())
            .build();
        o.replaceHighlights(buildHighlights());
        return o;
    }

    public void applyTo(Offering o) {
        o.setTitleEn(title);
        o.setTitleVi(titleVi);
        o.setCategory(category);
        o.setTaglineEn(tagline);
        o.setTaglineVi(taglineVi);
        o.setDescriptionEn(description);
        o.setDescriptionVi(descriptionVi);
        o.setPriceFrom(priceFrom);
        o.setCurrency(currency);
        o.setDurationEn(duration);
        o.setDurationVi(durationVi);
        o.setServesEn(serves);
        o.setServesVi(servesVi);
        o.setIllustration(illustration);
        o.setColor(color);
        o.replaceHighlights(buildHighlights());
    }

    private List<OfferingHighlight> buildHighlights() {
        boolean hasVi = highlightsVi != null && !highlightsVi.isEmpty();
        if (hasVi && highlightsVi.size() != highlights.size()) {
            throw new IllegalArgumentException(
                "highlightsVi must have the same length as highlights");
        }
        List<OfferingHighlight> out = new ArrayList<>();
        for (int i = 0; i < highlights.size(); i++) {
            out.add(OfferingHighlight.builder()
                .position(i)
                .textEn(highlights.get(i))
                .textVi(hasVi ? highlightsVi.get(i) : null)
                .build());
        }
        return out;
    }
}
