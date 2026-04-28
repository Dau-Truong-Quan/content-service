package garden.quan.content_service.plant;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static jakarta.persistence.criteria.JoinType.LEFT;

@Repository
public interface PlantRepository extends JpaRepository<Plant, UUID>, JpaSpecificationExecutor<Plant> {

    static Specification<Plant> hasCategory(String category) {
        return (root, q, cb) -> category == null ? null : cb.equal(root.get("category"), category);
    }

    static Specification<Plant> matchesSearch(String search) {
        return (root, q, cb) -> {
            if (search == null || search.isBlank()) return null;
            String like = "%" + search.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("commonNameEn")), like),
                cb.like(cb.lower(root.get("commonNameVi")), like),
                cb.like(cb.lower(root.get("latinName")), like),
                cb.like(cb.lower(root.get("family")), like),
                cb.like(cb.lower(root.get("notesEn")), like)
            );
        };
    }

    static Specification<Plant> hasTag(String tag) {
        return (root, q, cb) -> {
            if (tag == null || tag.isBlank()) return null;
            if (q != null) q.distinct(true);
            return cb.equal(root.join("tags", LEFT), tag);
        };
    }
}
