package garden.quan.content_service.journal;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record JournalEntryDto(
    UUID id,
    LocalDate date,
    String season,
    String title,
    String titleVi,
    String excerpt,
    String excerptVi,
    String body,
    String bodyVi,
    String weather,
    String weatherVi,
    String mood,
    int readMinutes
) {
    public static JournalEntryDto from(JournalEntry e) {
        return new JournalEntryDto(
            e.getId(), e.getDate(), e.getSeason(),
            e.getTitleEn(), e.getTitleVi(),
            e.getExcerptEn(), e.getExcerptVi(),
            e.getBodyEn(), e.getBodyVi(),
            e.getWeatherEn(), e.getWeatherVi(),
            e.getMood(), e.getReadMinutes()
        );
    }
}
