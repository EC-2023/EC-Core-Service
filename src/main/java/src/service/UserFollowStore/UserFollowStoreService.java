

package src.service.UserFollowStore;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.utils.ApiQuery;
import src.model.UserFollowStore;
import src.repository.IUserFollowStoreRepository;
import src.service.UserFollowStore.Dtos.UserFollowStoreDto;
import src.service.UserFollowStore.Dtos.UserFollowStoreUpdateDto;

import java.util.Date;
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

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<UserFollowStoreDto>> getAll() {
        return CompletableFuture.completedFuture(
                userfollowstoreRepository.findAll().stream().map(
                        x -> toDto.map(x, UserFollowStoreDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserFollowStoreDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userfollowstoreRepository.findById(id), UserFollowStoreDto.class));
    }

    @Async
    public CompletableFuture<UserFollowStoreDto> create(UUID userid) {
        UserFollowStore userfollowstore = new UserFollowStore();
        userfollowstore.setUserId(userid);
        return CompletableFuture.completedFuture(toDto.map(userfollowstoreRepository.save(userfollowstore), UserFollowStoreDto.class));
    }

    @Async
    public CompletableFuture<UserFollowStoreDto> update(UUID id, UserFollowStoreUpdateDto userfollowstore) {
        UserFollowStore existingUserFollowStore = userfollowstoreRepository.findById(id).orElse(null);
        if (existingUserFollowStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find User Follow Store!");
        BeanUtils.copyProperties(userfollowstore, existingUserFollowStore);
        existingUserFollowStore.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(userfollowstoreRepository.save(existingUserFollowStore), UserFollowStoreDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<UserFollowStoreDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = userfollowstoreRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<UserFollowStore> features = new ApiQuery<>(request, em, UserFollowStore.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, UserFollowStoreDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserFollowStore existingUserFollowStore = userfollowstoreRepository.findById(id).orElse(null);
        if (existingUserFollowStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find User Follow Store!");
        existingUserFollowStore.setIsDeleted(true);
        existingUserFollowStore.setUpdateAt(new Date(new java.util.Date().getTime()));
        userfollowstoreRepository.save(toDto.map(existingUserFollowStore, UserFollowStore.class));
        return CompletableFuture.completedFuture(null);
    }
}

