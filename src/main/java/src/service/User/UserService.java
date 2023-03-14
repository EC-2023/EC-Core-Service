

package src.service.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.User;
import src.repository.IUserRepository;
import src.service.User.Dtos.UserCreateDto;
import src.service.User.Dtos.UserDto;
import src.service.User.Dtos.UserUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<UserDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<UserDto>) userRepository.findAll().stream().map(
                        x -> toDto.map(x, UserDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userRepository.findById(id), UserDto.class));
    }

    @Async
    public CompletableFuture<UserDto> create(UserCreateDto input) {
        User user = userRepository.save(toDto.map(input, User.class));
        return CompletableFuture.completedFuture(toDto.map(userRepository.save(user), UserDto.class));
    }

    @Async
    public CompletableFuture<UserDto> update(UUID id, UserUpdateDto user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(userRepository.save(toDto.map(user, User.class)), UserDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingUser.setDeleted(true);
        userRepository.save(toDto.map(existingUser, User.class));
        return CompletableFuture.completedFuture(null);
    }
}

