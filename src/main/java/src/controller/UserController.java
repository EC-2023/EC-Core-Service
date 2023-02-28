package src.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.dto.SuccessResponseDto;
import src.config.utils.Utils;
import src.model.User;
import src.repository.IUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
//@ApiController("/users")
@ApiPrefixController(value = "/users")

public class UserController {

    @Autowired
    private IUserRepository     userRepository;

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Hello API")
////   @OpenApiResponse(model = PagedResultDto.class)
//    public PagedResultDto<testDto> getUsers(@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit) {
//        List<User> users = userRepository.findAll();
//        Pagination pagination = new Pagination(
//                10, 10, 10
//        );
//        return new PagedResultDto<testDto>(pagination, users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList()));
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "users", description = "Operations related to users")
    @Operation(summary = "Hello API")
    public PagedResultDto<testDto> getUsers(@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit) {
        List<User> users = userRepository.findAll();
        Pagination pagination = new Pagination(
                10, 10, 10
        );
        PagedResultDto<testDto> a =  new PagedResultDto<testDto>(pagination, users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList()));
        return new PagedResultDto<testDto>(pagination, users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList()));
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "users", description = "Operations related to users")
    @Operation(summary = "Get detail user")
    public SuccessResponseDto<testDto> getUser(@RequestParam(defaultValue = "1") UUID id) {
        Optional<User> user = userRepository.findById(id);
        return new SuccessResponseDto<>(Utils.toDto(user, testDto.class));
    }
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
