package src.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.utils.Utils;
import src.model.User;
import src.repository.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@ApiController("/users")
@ApiPrefixController(value = "/users")

public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @GetMapping
//    @Tag(name = "users", description = "Operations related to users")
    @Operation(summary = "Hello API")
    public PagedResultDto<testDto> getUsers(@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit) {
        List<User> users = userRepository.findAll();
//        users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList())
        Pagination pagination = new Pagination(
                10, 10, 10
        );

        return new PagedResultDto<testDto>(pagination, users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
