

package src.service.UserLevel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.exception.NotFoundException;
import src.model.UserLevel;
import src.repository.IUserLevelRepository;
import src.service.UserLevel.Dtos.UserLevelCreateDto;
import src.service.UserLevel.Dtos.UserLevelDto;
import src.service.UserLevel.Dtos.UserLevelUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserLevelService {
    // @Inject
    @Autowired
    private IUserLevelRepository userlevelRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<UserLevelDto>> getAll() {
        // search theo teen phaan trang
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
        return CompletableFuture.completedFuture(toDto.map(userlevel, UserLevelDto.class));
    }

    public CompletableFuture<UserLevelDto> update(UUID id, UserLevelUpdateDto userlevel) {
        UserLevel existingUserLevel = userlevelRepository.findById(id).orElse(null);
        if (existingUserLevel == null)
            throw new NotFoundException("Unable to find user level!");
        Date creatAt = existingUserLevel.getCreateAt();
        existingUserLevel = toDto.map(userlevel, UserLevel.class);
        existingUserLevel.setId(id);
        existingUserLevel.setCreateAt(creatAt);
        return CompletableFuture.completedFuture(toDto.map(userlevelRepository.save(existingUserLevel), UserLevelDto.class));
    }
    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserLevel existingUserLevel = userlevelRepository.findById(id).orElse(null);
        if (existingUserLevel == null)
            throw new NotFoundException("Unable to find user level!");
        existingUserLevel.setIsDeleted(true);
        userlevelRepository.save(existingUserLevel);
        return CompletableFuture.completedFuture(null);
    }
}

