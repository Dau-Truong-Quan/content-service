package garden.quan.content_service.journal;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record JournalEntryRequest(
    @NotNull LocalDate date,
    @NotBlank @Pattern(regexp = "spring|summer|autumn|winter") String season,
    @NotBlank String title,
    String titleVi,
    String excerpt,
    String excerptVi,
    @NotBlank String body,
    String bodyVi,
    String weather,
    String weatherVi,
    String mood,
    @Min(1) @Max(60) int readMinutes
) {
    public JournalEntry toEntity() {
        return JournalEntry.builder()
            .date(date).season(season)
            .titleEn(title).titleVi(titleVi)
            .excerptEn(excerpt).excerptVi(excerptVi)
            .bodyEn(body).bodyVi(bodyVi)
            .weatherEn(weather).weatherVi(weatherVi)
            .mood(mood).readMinutes(readMinutes)
            .build();
    }

    public void applyTo(JournalEntry e) {
        e.setDate(date);
        e.setSeason(season);
        e.setTitleEn(title);
        e.setTitleVi(titleVi);
        e.setExcerptEn(excerpt);
        e.setExcerptVi(excerptVi);
        e.setBodyEn(body);
        e.setBodyVi(bodyVi);
        e.setWeatherEn(weather);
        e.setWeatherVi(weatherVi);
        e.setMood(mood);
        e.setReadMinutes(readMinutes);
    }
}
