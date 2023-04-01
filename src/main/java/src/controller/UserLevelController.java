
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.UserLevel.Dtos.UserLevelCreateDto;
import src.service.UserLevel.Dtos.UserLevelDto;
import src.service.UserLevel.Dtos.UserLevelUpdateDto;
import src.service.UserLevel.IUserLevelService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController

@ApiPrefixController(value = "/userlevels") ///  api/v1/userlevels
public class UserLevelController {
    @Autowired
    private IUserLevelService userLevelService;

    @GetMapping("/{id}")
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserLevelDto> findOneById(@PathVariable UUID id) {
        return userLevelService.getOne(id);
    }

    @GetMapping()
//   @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<UserLevelDto>> findAll() {
        return userLevelService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<UserLevelDto>> findAllPagination(HttpServletRequest request,@RequestParam(required = false, defaultValue = "10") Integer limit ,
                                                                             @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                             @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return userLevelService.findAllPagination(request, limit, skip);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserLevelDto> create(@RequestBody UserLevelCreateDto input) {
        return userLevelService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    // Optional
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserLevelDto> update(@PathVariable UUID id, UserLevelUpdateDto userlevel) throws NoSuchFieldException, IllegalAccessException {
        return userLevelService.update(id, userlevel);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return userLevelService.remove(id);
    }


}
