
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.UserFollowProduct.Dtos.UserFollowProductCreateDto;
import src.service.UserFollowProduct.Dtos.UserFollowProductDto;
import src.service.UserFollowProduct.Dtos.UserFollowProductUpdateDto;
import src.service.UserFollowProduct.UserFollowProductService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/userfollowproducts")
public class UserFollowProductController {
    @Autowired
    private UserFollowProductService userfollowproductService;


    @GetMapping( "/{id}")
//    @Tag(name = "userfollowproducts", description = "Operations related to userfollowproducts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserFollowProductDto> findOneById(@PathVariable UUID id) {
        return userfollowproductService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "userfollowproducts", description = "Operations related to userfollowproducts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<UserFollowProductDto>> findAll() {
       return userfollowproductService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userfollowproducts", description = "Operations related to userfollowproducts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserFollowProductDto> create(@RequestBody UserFollowProductCreateDto input) {
        return userfollowproductService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userfollowproducts", description = "Operations related to userfollowproducts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserFollowProductDto> update(@PathVariable UUID id, UserFollowProductUpdateDto userfollowproduct) {
        return userfollowproductService.update(id, userfollowproduct);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userfollowproducts", description = "Operations related to userfollowproducts")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return userfollowproductService.remove(id);
    }
}
