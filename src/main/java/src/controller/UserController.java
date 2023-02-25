package src.controller;

import src.config.annotation.ApiPrefixController;
import src.model.User;
import src.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ApiPrefixController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
