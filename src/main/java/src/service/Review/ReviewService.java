

package src.service.Review;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.Review;
import src.repository.IReviewRepository;
import src.service.Review.Dtos.ReviewCreateDto;
import src.service.Review.Dtos.ReviewDto;
import src.service.Review.Dtos.ReviewUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ReviewService {
    @Autowired
    private IReviewRepository reviewRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<ReviewDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<ReviewDto>) reviewRepository.findAll().stream().map(
                        x -> toDto.map(x, ReviewDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<ReviewDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(reviewRepository.findById(id), ReviewDto.class));
    }

    @Async
    public CompletableFuture<ReviewDto> create(ReviewCreateDto input) {
        Review review = reviewRepository.save(toDto.map(input, Review.class));
        return CompletableFuture.completedFuture(toDto.map(reviewRepository.save(review), ReviewDto.class));
    }

    @Async
    public CompletableFuture<ReviewDto> update(UUID id, ReviewUpdateDto review) {
        Review existingReview = reviewRepository.findById(id).orElse(null);
        if (existingReview == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(reviewRepository.save(toDto.map(review, Review.class)), ReviewDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Review existingReview = reviewRepository.findById(id).orElse(null);
        if (existingReview == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingReview.setIsDeleted(true);
        reviewRepository.save(toDto.map(existingReview, Review.class));
        return CompletableFuture.completedFuture(null);
    }
}

