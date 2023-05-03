
package src.service.Review;

import src.service.IService;
import src.service.Review.Dtos.ReviewCreateDto;
import src.service.Review.Dtos.ReviewDto;
import src.service.Review.Dtos.ReviewUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IReviewService extends IService<ReviewDto, ReviewCreateDto, ReviewUpdateDto> {
    public CompletableFuture<ReviewDto> create(UUID userId, ReviewCreateDto review);

    public CompletableFuture<List<ReviewDto>> getReviewByProduct(UUID productId);

    public CompletableFuture<Boolean> checkBuy(UUID productId, UUID userId);
}
