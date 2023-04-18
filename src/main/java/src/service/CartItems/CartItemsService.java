

package src.service.CartItems;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.CartItems;
import src.repository.ICartItemsRepository;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.Dtos.CartItemsUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
public class CartItemsService implements ICartItemsService {
    @Autowired
    private ICartItemsRepository cartitemsRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<CartItemsDto>> getAll() {
        return CompletableFuture.completedFuture(
                cartitemsRepository.findAll().stream().map(
                        x -> toDto.map(x, CartItemsDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<CartItemsDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(cartitemsRepository.findById(id).get(), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<CartItemsDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = cartitemsRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<CartItems> features = new ApiQuery<>(request, em, CartItems.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, CartItemsDto.class)).toList()));
    }

    @Async
    public CompletableFuture<CartItemsDto> create(CartItemsCreateDto input) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CartItems cartitems = toDto.map(input, CartItems.class);

        return CompletableFuture.completedFuture(toDto.map(cartitemsRepository.save(cartitems), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<CartItemsDto> update(UUID id, CartItemsUpdateDto cartitems) {
        CartItems existingCartItems = cartitemsRepository.findById(id).orElse(null);
        if (existingCartItems == null)
            throw new NotFoundException("Unable to find cart items!");
        BeanUtils.copyProperties(cartitems, existingCartItems);
        existingCartItems.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(cartitemsRepository.save(existingCartItems), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        CartItems existingCartItems = cartitemsRepository.findById(id).orElse(null);
        if (existingCartItems == null)
            throw new NotFoundException("Unable to find cart items!");
        existingCartItems.setIsDeleted(true);
        existingCartItems.setUpdateAt(new Date(new java.util.Date().getTime()));
        cartitemsRepository.save(toDto.map(existingCartItems, CartItems.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Boolean> removeByCartIdAndProductId(UUID cartId, UUID productId) {
        CartItems cartItems = cartitemsRepository.findCartItemsByCartIdAndProductId(cartId, productId);

        if (cartItems != null) {
            cartitemsRepository.delete(cartItems);
            return CompletableFuture.completedFuture(true);
        } else {
            throw new NotFoundException("Not found cart item by cartId: " + cartId + " and productId: " + productId);
        }
    }
}

