
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findAllByParentCategoryId(UUID parentCategoryId);
}

    