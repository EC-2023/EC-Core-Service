
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.dto.PagedResultDto;
import src.service.User.Dtos.UserCreateDto;
import src.service.User.Dtos.UserDto;
import src.service.User.Dtos.UserProfileDto;
import src.service.User.Dtos.UserUpdateDto;
import src.service.User.IUserService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }


    @GetMapping( "/{id}")
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserDto> findOneById(@PathVariable UUID id) {
        return userService.getOne(id);
    }

    @GetMapping()
    // khi dddawng nhap vao thi moi dung duoc chuc nang
    // check role co duoc quyen goi api k
//    @Authenticate
//    @RequiresAuthorization("string")
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<UserDto>> findAll() {
       return userService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserDto> create(@RequestBody UserCreateDto input) {
        return userService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserDto> update(@PathVariable UUID id, UserUpdateDto user) {
        return userService.update(id, user);
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<UserDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page ,
                                                                              @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                              @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return userService.findAllPagination(request, size, page * size);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return userService.remove(id);
    }

    @Authenticate
    @GetMapping(value = "my-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<UserProfileDto> getMyProfile(){
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));

        return userService.getMyProfile(userId);
    }

    @Authenticate
    @PatchMapping(value = "my-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<UserProfileDto> updateMyProfile(@RequestBody UserUpdateDto input){
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return userService.updateMyProfile(userId, input);
    }
}
