

package src.service.UserFollowProduct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.UserFollowProduct;
import src.repository.IUserFollowProductRepository;
import src.service.UserFollowProduct.Dtos.UserFollowProductCreateDto;
import src.service.UserFollowProduct.Dtos.UserFollowProductDto;
import src.service.UserFollowProduct.Dtos.UserFollowProductUpdateDto;

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

    @Async
    public CompletableFuture<List<UserFollowProductDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<UserFollowProductDto>) userfollowproductRepository.findAll().stream().map(
                        x -> toDto.map(x, UserFollowProductDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserFollowProductDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userfollowproductRepository.findById(id), UserFollowProductDto.class));
    }

    @Async
    public CompletableFuture<UserFollowProductDto> create(UserFollowProductCreateDto input) {
        UserFollowProduct userfollowproduct = userfollowproductRepository.save(toDto.map(input, UserFollowProduct.class));
        return CompletableFuture.completedFuture(toDto.map(userfollowproductRepository.save(userfollowproduct), UserFollowProductDto.class));
    }

    @Async
    public CompletableFuture<UserFollowProductDto> update(UUID id, UserFollowProductUpdateDto userfollowproduct) {
        UserFollowProduct existingUserFollowProduct = userfollowproductRepository.findById(id).orElse(null);
        if (existingUserFollowProduct == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(userfollowproductRepository.save(toDto.map(userfollowproduct, UserFollowProduct.class)), UserFollowProductDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserFollowProduct existingUserFollowProduct = userfollowproductRepository.findById(id).orElse(null);
        if (existingUserFollowProduct == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingUserFollowProduct.setDeleted(true);
        userfollowproductRepository.save(toDto.map(existingUserFollowProduct, UserFollowProduct.class));
        return CompletableFuture.completedFuture(null);
    }
}

