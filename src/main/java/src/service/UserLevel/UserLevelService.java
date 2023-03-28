

package src.service.UserLevel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.UserLevel;
import src.repository.IUserLevelRepository;
import src.service.UserLevel.Dtos.UserLevelCreateDto;
import src.service.UserLevel.Dtos.UserLevelDto;
import src.service.UserLevel.Dtos.UserLevelUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserLevelService implements IUserLevelService {
    // @Inject
    @Autowired
    private IUserLevelRepository userLevelRepository;
    @Autowired
    private ModelMapper toDto;
    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<UserLevelDto>> getAll() {
        // search theo teen phaan trang
        return CompletableFuture.completedFuture(
                (List<UserLevelDto>) userLevelRepository.findAll().stream().map(
                        x -> toDto.map(x, UserLevelDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserLevelDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userLevelRepository.findById(id).get(), UserLevelDto.class));
    }

    @Async
    public CompletableFuture<UserLevelDto> create(UserLevelCreateDto input) {
        UserLevel userlevel = userLevelRepository.save(toDto.map(input, UserLevel.class));
        return CompletableFuture.completedFuture(toDto.map(userlevel, UserLevelDto.class));
    }

    public CompletableFuture<UserLevelDto> update(UUID id, UserLevelUpdateDto userlevel) {
        UserLevel existingUserLevel = userLevelRepository.findById(id).orElse(null);
        if (existingUserLevel == null)
            throw new NotFoundException("Unable to find user level!");
        BeanUtils.copyProperties(userlevel, existingUserLevel);
        return CompletableFuture.completedFuture(toDto.map(userLevelRepository.save(existingUserLevel), UserLevelDto.class));
    }

    //     first name pagination
    @Async
    public CompletableFuture<PagedResultDto<UserLevelDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        ApiQuery<UserLevel> features = new ApiQuery<>(request, em, UserLevel.class);
        long total = userLevelRepository.count();
        return CompletableFuture.completedFuture(PagedResultDto.create(Pagination.create(total, skip, limit),
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, UserLevelDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserLevel existingUserLevel = userLevelRepository.findById(id).orElse(null);
        if (existingUserLevel == null)
            throw new NotFoundException("Unable to find user level!");
        existingUserLevel.setIsDeleted(true);
        userLevelRepository.save(existingUserLevel);
        return CompletableFuture.completedFuture(null);
    }
}

