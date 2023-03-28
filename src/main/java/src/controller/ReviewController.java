
package src.controller;

import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.model.User;
import src.service.Review.Dtos.ReviewCreateDto;
import src.service.Review.Dtos.ReviewDto;
import src.service.Review.Dtos.ReviewUpdateDto;
import src.service.Review.ReviewService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;


    @GetMapping( "/{id}")
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
//    @Tag(name = "reviews", description = "Operations related to reviews")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ReviewDto> create(ServletRequest request, @RequestBody ReviewCreateDto input) {

        User user = (User) request.getAttribute("user");
     // map qua model
        // twf model set user_id tuwf user.getId()
        return reviewService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "reviews", description = "Operations related to reviews")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ReviewDto> update(@PathVariable UUID id, ReviewUpdateDto review) {
        return reviewService.update(id, review);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "reviews", description = "Operations related to reviews")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return reviewService.remove(id);
    }
}
