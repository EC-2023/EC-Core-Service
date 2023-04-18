

package src.service.UserFollowProduct;

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
import src.model.UserFollowProduct;
import src.repository.IUserFollowProductRepository;
import src.service.UserFollowProduct.Dtos.UserFollowProductDto;
import src.service.UserFollowProduct.Dtos.UserFollowProductUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserFollowProductService {
    @Autowired
    private IUserFollowProductRepository userfollowproductRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<UserFollowProductDto>> getAll() {
        return CompletableFuture.completedFuture(
                userfollowproductRepository.findAll().stream().map(
                        x -> toDto.map(x, UserFollowProductDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserFollowProductDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userfollowproductRepository.findById(id), UserFollowProductDto.class));
    }

    @Async
    public CompletableFuture<UserFollowProductDto> create(UUID userid) {
        UserFollowProduct userfollowproduct = new UserFollowProduct();
        userfollowproduct.setUserId(userid);
        return CompletableFuture.completedFuture(toDto.map(userfollowproductRepository.save(userfollowproduct), UserFollowProductDto.class));
    }

    @Async
    public CompletableFuture<UserFollowProductDto> update(UUID id, UserFollowProductUpdateDto userfollowproduct) {
        UserFollowProduct existingUserFollowProduct = userfollowproductRepository.findById(id).orElse(null);
        if (existingUserFollowProduct == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find User Follow Product");
        BeanUtils.copyProperties(userfollowproduct, existingUserFollowProduct);
        existingUserFollowProduct.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(userfollowproductRepository.save(existingUserFollowProduct), UserFollowProductDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<UserFollowProductDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = userfollowproductRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<UserFollowProduct> features = new ApiQuery<>(request, em, UserFollowProduct.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, UserFollowProductDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserFollowProduct existingUserFollowProduct = userfollowproductRepository.findById(id).orElse(null);
        if (existingUserFollowProduct == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find User Follow Product!");
        existingUserFollowProduct.setDeleted(true);
        existingUserFollowProduct.setUpdateAt(new Date(new java.util.Date().getTime()));
        userfollowproductRepository.save(toDto.map(existingUserFollowProduct, UserFollowProduct.class));
        return CompletableFuture.completedFuture(null);
    }
}

