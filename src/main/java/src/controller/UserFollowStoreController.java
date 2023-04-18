
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.dto.PagedResultDto;
import src.service.UserFollowStore.Dtos.UserFollowStoreDto;
import src.service.UserFollowStore.Dtos.UserFollowStoreCreateDto;
import src.service.UserFollowStore.Dtos.UserFollowStoreDto;
import src.service.UserFollowStore.Dtos.UserFollowStoreUpdateDto;
import src.service.UserFollowStore.UserFollowStoreService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/userfollowstores")
public class UserFollowStoreController {
    @Autowired
    private UserFollowStoreService userfollowstoreService;


    @GetMapping( "/{id}")
//    @Tag(name = "userfollowstores", description = "Operations related to userfollowstores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserFollowStoreDto> findOneById(@PathVariable UUID id) {
        return userfollowstoreService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "userfollowstores", description = "Operations related to userfollowstores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<UserFollowStoreDto>> findAll() {
        return userfollowstoreService.getAll();
    }


    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<UserFollowStoreDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page ,
                                                                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                                   @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return userfollowstoreService.findAllPagination(request, size, page * size);
    }

    @Authenticate
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userfollowstores", description = "Operations related to userfollowstores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserFollowStoreDto> create() {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return userfollowstoreService.create(userId);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userfollowstores", description = "Operations related to userfollowstores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserFollowStoreDto> update(@PathVariable UUID id, UserFollowStoreUpdateDto userfollowstore) {
        return userfollowstoreService.update(id, userfollowstore);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userfollowstores", description = "Operations related to userfollowstores")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return userfollowstoreService.remove(id);
    }
}
