
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.dto.PagedResultDto;
import src.service.Review.Dtos.ReviewCreateDto;
import src.service.Review.Dtos.ReviewDto;
import src.service.Review.Dtos.ReviewUpdateDto;
import src.service.Review.IReviewService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/reviews")
public class ReviewController {
    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping("/{id}")
//    @Tag(name = "reviews", description = "Operations related to reviews")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ReviewDto> findOneById(@PathVariable UUID id) {
        return reviewService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "reviews", description = "Operations related to reviews")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<ReviewDto>> findAll() {
        return reviewService.getAll();
    }

    @Authenticate
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ReviewDto> create(@RequestBody ReviewCreateDto input) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return reviewService.create(userId, input);
    }

    @Authenticate
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "reviews", description = "Operations related to reviews")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ReviewDto> update(@PathVariable UUID id, ReviewUpdateDto review) {
        return reviewService.update(id, review);
    }

    @Authenticate
    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<ReviewDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                          @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                                          @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return reviewService.findAllPagination(request, limit, skip);
    }

    @Authenticate
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "reviews", description = "Operations related to reviews")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return reviewService.remove(id);
    }

    @GetMapping(value = "/get-by-product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<List<ReviewDto>> getReviewByProduct(@PathVariable UUID productId) {
        return reviewService.getReviewByProduct(productId);

    }

    @Authenticate
    @GetMapping(value = "/check-buy", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<Boolean> checkBuy(@RequestParam UUID productId) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return reviewService.checkBuy(userId, productId);
    }

}
