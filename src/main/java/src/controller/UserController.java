package src.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import src.config.annotation.ApiPrefixController;
import src.config.swagger.PaginatedApiResponse;
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
    @PaginatedApiResponse(defaultPageSize = 20)
    public List<testDto> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
