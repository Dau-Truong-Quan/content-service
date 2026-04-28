package garden.quan.content_service.gallery;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GalleryImageDto(
    UUID id,
    String caption,
    String captionVi,
    String season,
    int year,
    String ratio,
    /** [from, to] gradient stops, matching the frontend's tuple shape. */
    List<String> palette,
    String illustration
) {
    public static GalleryImageDto from(GalleryImage g) {
        return new GalleryImageDto(
            g.getId(), g.getCaptionEn(), g.getCaptionVi(),
            g.getSeason(), g.getYear(), g.getRatio(),
            List.of(g.getPaletteFrom(), g.getPaletteTo()),
            g.getIllustration()
        );
    }
}
