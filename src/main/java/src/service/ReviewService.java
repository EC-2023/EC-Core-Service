
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Review;
import src.repository.IReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
    private  IReviewRepository reviewRepository;
    @Autowired
    public ReviewService(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAll() {
        return (List<Review>) reviewRepository.findAll();
    }

    public Optional<Review> getOne(UUID id) {
        return reviewRepository.findById(id);
    }

    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    public Review update(UUID id, Review review) {
        Review existingReview = reviewRepository.findById(id).orElse(null);
        if (existingReview != null) {

            reviewRepository.save(existingReview);
        }
        return existingReview;
    }

    public void remove(UUID id) {
        reviewRepository.deleteById(id);
    }
}
