
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    //    @Query("SELECT c FROM Category c WHERE c.Id = ?1")
    @Query(value = "SELECT * FROM category WHERE category_id = ?1", nativeQuery = true)
    Optional<Category> findById(UUID id);

    List<Category> findAllByParentCategoryId(UUID parentCategoryId);
}

    