
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.Category;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, UUID> {
    //    @Query("SELECT c FROM Category c WHERE c.Id = ?1")
    @Query("SELECT c FROM Category c WHERE c.parentCategoryId IS NOT NULL")
    List<Category> findFeatureCategories();


    List<Category> findByNameStartingWithIgnoreCase(String name);
}

    