
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.Review;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReviewRepository extends JpaRepository<Review, UUID> {
    @Query("SELECT p FROM Review p WHERE p.productId =  ?1")
    List<Review> getReviewByProduct(UUID productId);


    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId = ?1")
    Double findAverageRatingByProductId(UUID productId);
}

    