
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.model.Review;

import java.util.UUID;

@Repository
public interface IReviewRepository extends JpaRepository<Review, UUID> {
}

    