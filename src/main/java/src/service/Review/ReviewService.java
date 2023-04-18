

package src.service.Review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.utils.ApiQuery;
import src.model.Review;
import src.repository.IReviewRepository;
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

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<ReviewDto>> getAll() {
        return CompletableFuture.completedFuture(
                reviewRepository.findAll().stream().map(
                        x -> toDto.map(x, ReviewDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<ReviewDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(reviewRepository.findById(id), ReviewDto.class));
    }

    @Async
    public CompletableFuture<ReviewDto> create(UUID userid) {
        Review review = new Review();
        review.setUserId(userid);
        return CompletableFuture.completedFuture(toDto.map(reviewRepository.save(review), ReviewDto.class));
    }

    @Async
    public CompletableFuture<ReviewDto> update(UUID id, ReviewUpdateDto reviews) {
        Review existingReview = reviewRepository.findById(id).orElse(null);
        if (existingReview == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find Review!");
        BeanUtils.copyProperties(reviews, existingReview);
        return CompletableFuture.completedFuture(toDto.map(reviewRepository.save(existingReview), ReviewDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<ReviewDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = reviewRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<Review> features = new ApiQuery<>(request, em, Review.class,pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, ReviewDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Review existingReview = reviewRepository.findById(id).orElse(null);
        if (existingReview == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find Review!");
        existingReview.setIsDeleted(true);
        reviewRepository.save(toDto.map(existingReview, Review.class));
        return CompletableFuture.completedFuture(null);
    }
}

