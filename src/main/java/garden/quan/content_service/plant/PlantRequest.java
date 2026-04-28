package garden.quan.content_service.plant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

/** Admin write payload for create + update. */
public record PlantRequest(
    @NotBlank String commonName,
    String commonNameVi,
    @NotBlank String latinName,
    @NotBlank @Pattern(regexp = "flowering|foliage|edible|herb|tree|climber|succulent",
        message = "must be one of: flowering, foliage, edible, herb, tree, climber, succulent")
    String category,
    @NotBlank String family,
    @NotBlank @Pattern(regexp = "full-sun|partial-shade|shade") String light,
    @NotBlank @Pattern(regexp = "low|moderate|high") String water,
    @NotBlank @Pattern(regexp = "easy|moderate|challenging") String difficulty,
    List<String> bloomSeason,
    @NotBlank String height,
    String heightVi,
    String notes,
    String notesVi,
    String story,
    String storyVi,
    LocalDate plantedOn,
    @NotBlank @Pattern(regexp = "#[0-9a-fA-F]{6}",
        message = "must be a 6-digit hex colour, e.g. #c4837a") String color,
    @NotBlank String illustration,
    List<String> tags
) {
    public Plant toEntity() {
        return Plant.builder()
            .commonNameEn(commonName)
            .commonNameVi(commonNameVi)
            .latinName(latinName)
            .category(category)
            .family(family)
            .light(light)
            .water(water)
            .difficulty(difficulty)
            .bloomSeason(bloomSeason == null ? List.of() : bloomSeason)
            .heightEn(height)
            .heightVi(heightVi)
            .notesEn(notes)
            .notesVi(notesVi)
            .storyEn(story)
            .storyVi(storyVi)
            .plantedOn(plantedOn)
            .color(color)
            .illustration(illustration)
            .tags(tags == null ? List.of() : tags)
            .build();
    }

    public void applyTo(Plant p) {
        p.setCommonNameEn(commonName);
        p.setCommonNameVi(commonNameVi);
        p.setLatinName(latinName);
        p.setCategory(category);
        p.setFamily(family);
        p.setLight(light);
        p.setWater(water);
        p.setDifficulty(difficulty);
        p.getBloomSeason().clear();
        if (bloomSeason != null) p.getBloomSeason().addAll(bloomSeason);
        p.setHeightEn(height);
        p.setHeightVi(heightVi);
        p.setNotesEn(notes);
        p.setNotesVi(notesVi);
        p.setStoryEn(story);
        p.setStoryVi(storyVi);
        p.setPlantedOn(plantedOn);
        p.setColor(color);
        p.setIllustration(illustration);
        p.getTags().clear();
        if (tags != null) p.getTags().addAll(tags);
    }
}
