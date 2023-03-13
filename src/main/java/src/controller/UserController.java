
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.annotation.RequiresAuthorization;
import src.config.utils.Constant;
import src.service.User.Dtos.UserCreateDto;
import src.service.User.Dtos.UserDto;
import src.service.User.Dtos.UserUpdateDto;
import src.service.User.UserService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping( "/{id}")
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserDto> findOneById(@PathVariable UUID id) {
        return userService.getOne(id);
    }

    @GetMapping()
    @Authenticate
    @RequiresAuthorization(Constant.ADMIN)
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

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return userService.remove(id);
    }
}
