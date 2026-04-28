package garden.quan.content_service.gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryImage, UUID> {

    @Query("select g from GalleryImage g " +
           "where (:season is null or g.season = :season) " +
           "order by g.year desc, g.captionEn")
    List<GalleryImage> search(String season);
}
