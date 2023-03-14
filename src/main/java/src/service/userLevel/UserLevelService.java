

package src.service.UserLevel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.UserLevel;
import src.repository.IUserLevelRepository;
import src.service.UserLevel.Dtos.UserLevelCreateDto;
import src.service.UserLevel.Dtos.UserLevelDto;
import src.service.UserLevel.Dtos.UserLevelUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserLevelService {
    @Autowired
    private IUserLevelRepository userlevelRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<UserLevelDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<UserLevelDto>) userlevelRepository.findAll().stream().map(
                        x -> toDto.map(x, UserLevelDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserLevelDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userlevelRepository.findById(id), UserLevelDto.class));
    }

    @Async
    public CompletableFuture<UserLevelDto> create(UserLevelCreateDto input) {
        UserLevel userlevel = userlevelRepository.save(toDto.map(input, UserLevel.class));
        return CompletableFuture.completedFuture(toDto.map(userlevelRepository.save(userlevel), UserLevelDto.class));
    }

    @Async
    public CompletableFuture<UserLevelDto> update(UUID id, UserLevelUpdateDto userlevel) {
        UserLevel existingUserLevel = userlevelRepository.findById(id).orElse(null);
        if (existingUserLevel == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(userlevelRepository.save(toDto.map(userlevel, UserLevel.class)), UserLevelDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserLevel existingUserLevel = userlevelRepository.findById(id).orElse(null);
        if (existingUserLevel == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingUserLevel.setDeleted(true);
        userlevelRepository.save(toDto.map(existingUserLevel, UserLevel.class));
        return CompletableFuture.completedFuture(null);
    }
}

