package garden.quan.content_service.journal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {

    @Query("select j from JournalEntry j " +
           "where (:season is null or j.season = :season) " +
           "order by j.date desc")
    List<JournalEntry> search(String season);
}
