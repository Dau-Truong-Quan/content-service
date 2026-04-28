package garden.quan.content_service.plant;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Public read shape. Mirrors the frontend's {@code Plant} TS interface
 * exactly, including the optional {@code *Vi} bilingual fields.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlantDto(
    UUID id,
    String commonName,
    String commonNameVi,
    String latinName,
    String category,
    String family,
    String light,
    String water,
    String difficulty,
    List<String> bloomSeason,
    String height,
    String heightVi,
    String notes,
    String notesVi,
    String story,
    String storyVi,
    LocalDate plantedOn,
    String color,
    String illustration,
    List<String> tags
) {
    public static PlantDto from(Plant p) {
        return new PlantDto(
            p.getId(),
            p.getCommonNameEn(),
            p.getCommonNameVi(),
            p.getLatinName(),
            p.getCategory(),
            p.getFamily(),
            p.getLight(),
            p.getWater(),
            p.getDifficulty(),
            p.getBloomSeason() == null || p.getBloomSeason().isEmpty() ? null : p.getBloomSeason(),
            p.getHeightEn(),
            p.getHeightVi(),
            p.getNotesEn(),
            p.getNotesVi(),
            p.getStoryEn(),
            p.getStoryVi(),
            p.getPlantedOn(),
            p.getColor(),
            p.getIllustration(),
            p.getTags()
        );
    }
}
