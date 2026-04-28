package garden.quan.content_service.gallery;

import jakarta.validation.constraints.*;

import java.util.List;

public record GalleryImageRequest(
    @NotBlank String caption,
    String captionVi,
    @NotBlank @Pattern(regexp = "spring|summer|autumn|winter") String season,
    @Min(1900) @Max(2200) int year,
    @NotBlank @Pattern(regexp = "portrait|landscape|square") String ratio,
    @NotNull @Size(min = 2, max = 2, message = "palette must contain exactly two hex colours")
    List<@Pattern(regexp = "#[0-9a-fA-F]{6}") String> palette,
    @NotBlank String illustration
) {
    public GalleryImage toEntity() {
        return GalleryImage.builder()
            .captionEn(caption).captionVi(captionVi)
            .season(season).year(year).ratio(ratio)
            .paletteFrom(palette.get(0)).paletteTo(palette.get(1))
            .illustration(illustration)
            .build();
    }

    public void applyTo(GalleryImage g) {
        g.setCaptionEn(caption);
        g.setCaptionVi(captionVi);
        g.setSeason(season);
        g.setYear(year);
        g.setRatio(ratio);
        g.setPaletteFrom(palette.get(0));
        g.setPaletteTo(palette.get(1));
        g.setIllustration(illustration);
    }
}
