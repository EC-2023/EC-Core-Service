
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.userLevel.Dtos.UserLevelCreateDto;
import src.service.userLevel.Dtos.UserLevelDto;
import src.service.userLevel.Dtos.UserLevelUpdateDto;
import src.service.userLevel.UserLevelService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/userlevels")
public class UserLevelController {
    @Autowired
    private UserLevelService userlevelService;


    @GetMapping( "/{id}")
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserLevelDto> findOneById(@PathVariable UUID id) {
        return userlevelService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<UserLevelDto>> findAll() {
       return userlevelService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserLevelDto> create(@RequestBody UserLevelCreateDto input) {
        return userlevelService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserLevelDto> update(@PathVariable UUID id, UserLevelUpdateDto userlevel) {
        return userlevelService.update(id, userlevel);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "userlevels", description = "Operations related to userlevels")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return userlevelService.remove(id);
    }
}
