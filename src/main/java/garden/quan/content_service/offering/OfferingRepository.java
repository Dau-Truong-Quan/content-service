package garden.quan.content_service.offering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, UUID> {

    @Query("select distinct o from Offering o left join fetch o.highlights " +
           "where (:category is null or o.category = :category) " +
           "order by o.titleEn")
    List<Offering> search(String category);
}
