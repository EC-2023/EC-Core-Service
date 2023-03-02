

package src.service.UserFollowStore;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.UserFollowStore;
import src.repository.IUserFollowStoreRepository;
import src.service.UserFollowStore.Dtos.UserFollowStoreCreateDto;
import src.service.UserFollowStore.Dtos.UserFollowStoreDto;
import src.service.UserFollowStore.Dtos.UserFollowStoreUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserFollowStoreService {
    @Autowired
    private IUserFollowStoreRepository userfollowstoreRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<UserFollowStoreDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<UserFollowStoreDto>) userfollowstoreRepository.findAll().stream().map(
                        x -> toDto.map(x, UserFollowStoreDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserFollowStoreDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userfollowstoreRepository.findById(id), UserFollowStoreDto.class));
    }

    @Async
    public CompletableFuture<UserFollowStoreDto> create(UserFollowStoreCreateDto input) {
        UserFollowStore userfollowstore = userfollowstoreRepository.save(toDto.map(input, UserFollowStore.class));
        return CompletableFuture.completedFuture(toDto.map(userfollowstoreRepository.save(userfollowstore), UserFollowStoreDto.class));
    }

    @Async
    public CompletableFuture<UserFollowStoreDto> update(UUID id, UserFollowStoreUpdateDto userfollowstore) {
        UserFollowStore existingUserFollowStore = userfollowstoreRepository.findById(id).orElse(null);
        if (existingUserFollowStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(userfollowstoreRepository.save(toDto.map(userfollowstore, UserFollowStore.class)), UserFollowStoreDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserFollowStore existingUserFollowStore = userfollowstoreRepository.findById(id).orElse(null);
        if (existingUserFollowStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingUserFollowStore.setDeleted(true);
        userfollowstoreRepository.save(toDto.map(existingUserFollowStore, UserFollowStore.class));
        return CompletableFuture.completedFuture(null);
    }
}

