

package src.service.CartItems;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.AttributeValue;
import src.model.Cart;
import src.model.CartItems;
import src.model.User;
import src.repository.IAttributeValueRepository;
import src.repository.ICartItemsRepository;
import src.repository.ICartRepository;
import src.repository.IUserRepository;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.Dtos.CartItemsUpdateDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
public class CartItemsService implements ICartItemsService {
    private final ICartItemsRepository cartItemsRepository;
    private final ICartRepository cartRepository;
    private final IAttributeValueRepository attributeValueRepository;
    final
    IUserRepository userRepository;
    private final ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    public CartItemsService(ICartItemsRepository cartItemsRepository, ICartRepository cartRepository, IAttributeValueRepository attributeValueRepository, IUserRepository userRepository, ModelMapper toDto) {
        this.cartItemsRepository = cartItemsRepository;
        this.cartRepository = cartRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.userRepository = userRepository;
        this.toDto = toDto;
    }

    @Async
    public CompletableFuture<List<CartItemsDto>> getAll() {
        return CompletableFuture.completedFuture(
                cartItemsRepository.findAll().stream().map(
                        x -> toDto.map(x, CartItemsDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<CartItemsDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(cartItemsRepository.findById(id).get(), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<CartItemsDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = cartItemsRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<CartItems> features = new ApiQuery<>(request, em, CartItems.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, CartItemsDto.class)).toList()));
    }

    @Async
    public CompletableFuture<CartItemsDto> create(CartItemsCreateDto input) {
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CartItems cartitems = toDto.map(input, CartItems.class);
        cartitems = cartItemsRepository.save(cartitems);
        if (input.getAttributesValues().size() > 0) {
            List<AttributeValue> attributeValues = new ArrayList<>();
            for (String attributeValue : input.getAttributesValues()) {
                attributeValues.add(new AttributeValue(null, cartitems.getId(), null, null, attributeValue));
            }
            attributeValueRepository.saveAll(attributeValues);
        }
        return CompletableFuture.completedFuture(toDto.map(cartitems, CartItemsDto.class));
    }

    @Async
    public CompletableFuture<CartItemsDto> update(UUID id, CartItemsUpdateDto cartitems) {
        CartItems existingCartItems = cartItemsRepository.findById(id).orElse(null);
        if (existingCartItems == null)
            throw new NotFoundException("Unable to find cart items!");
        BeanUtils.copyProperties(cartitems, existingCartItems);
        existingCartItems.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(cartItemsRepository.save(existingCartItems), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        CartItems existingCartItems = cartItemsRepository.findById(id).orElse(null);
        if (existingCartItems == null)
            throw new NotFoundException("Unable to find cart items!");
        existingCartItems.setIsDeleted(true);
        existingCartItems.setUpdateAt(new Date(new java.util.Date().getTime()));
        cartItemsRepository.save(toDto.map(existingCartItems, CartItems.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<Boolean> removeCartItem(UUID cartItemId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found user by id: " + userId));
        List<Cart> cart = cartRepository.findCartsByUserId(userId);
        if (cart.size() == 0) {
            throw new NotFoundException("Not found cart by userId: " + userId);
        }
        CartItems cartItems = cartItemsRepository.findById(cartItemId).orElseThrow(() -> new NotFoundException("Not found cart item by id: " + cartItemId));
        if (!containsItem(cart, cartItemId)) {
            throw new NotFoundException("You don't have permission to delete this cart item!");
        }
        List<AttributeValue> attributeValues = attributeValueRepository.findByCartItem_id(cartItems.getId());
        if (attributeValues.size() > 0) {
            attributeValueRepository.deleteAll(attributeValues);
        }
        attributeValueRepository.deleteAll(attributeValues);
        cartItemsRepository.delete(cartItems);
        return CompletableFuture.completedFuture(true);
    }

    public boolean containsItem(List<Cart> carts, UUID itemId) {
        for (Cart item : carts) {
            if (item.getId().equals(itemId)) {
                return true;
            }
        }
        return false;
    }
}

