

package src.service.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.Cart;
import src.model.Cart;
import src.model.User;
import src.repository.ICartRepository;
import src.service.Cart.Dtos.CartCreateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.Cart.Dtos.CartUpdateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.CartItems.CartItemsService;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.ICartItemsService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private ModelMapper toDto;
    @Autowired
    ICartItemsService cartItemsService;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<CartDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<CartDto>) cartRepository.findAll().stream().map(
                        x -> toDto.map(x, CartDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<CartDto> getOne(UUID id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);

        if (cartOptional.isEmpty()) {
            throw new NotFoundException("Not found cart by id " + id);
        }

        return CompletableFuture.completedFuture(toDto.map(cartOptional.get(), CartDto.class));
    }

    @Async
    public CompletableFuture<CartDto> getOneByUserId(UUID userId) {

        List<Cart> carts = cartRepository.findCartsByUserId(userId);

        carts.sort((cart1, cart2) -> cart2.getUpdateAt().compareTo(cart1.getUpdateAt()));

        if (carts.size() > 0) {
            return CompletableFuture.completedFuture(toDto.map(carts.get(0), CartDto.class));
        }

        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<PagedResultDto<CartDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        ApiQuery<Cart> features = new ApiQuery<>(request, em, Cart.class);
        long total = cartRepository.count();
        return CompletableFuture.completedFuture(PagedResultDto.create(Pagination.create(total, skip, limit),
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, CartDto.class)).toList()));
    }

    @Async
    public CompletableFuture<CartDto> create(CartCreateDto input) {
        return null;
    }

    @Async
    public CompletableFuture<CartDto> create(UUID userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        return CompletableFuture.completedFuture(toDto.map(cartRepository.save(cart), CartDto.class));
    }

    @Async
    public CompletableFuture<CartDto> update(UUID id, CartUpdateDto cart) {
        Cart existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart == null)
            throw new NotFoundException("Unable to find cart!");
        BeanUtils.copyProperties(cart, existingCart);
        existingCart.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(cartRepository.save(existingCart), CartDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Cart existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart == null)
            throw new NotFoundException("Unable to find cart!");
        existingCart.setIsDeleted(true);
        existingCart.setUpdateAt(new Date(new java.util.Date().getTime()));
        cartRepository.save(toDto.map(existingCart, Cart.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public  CompletableFuture<CartItemsDto> addToCart(CartItemsCreateDto cartItemsCreateDto, UUID userId) {

        CartDto cartDto = null;

        try {
            cartDto = getOneByUserId(userId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (cartDto == null || cartDto.isDeleted == null || cartDto.isDeleted) {
            try {
                cartDto = create(userId).get();
            } catch (InterruptedException | ExecutionException e) {
                cartDto = null;
                e.printStackTrace();
            }
        }

        if (cartDto != null) {
            cartItemsCreateDto.setCartId(cartDto.getId());

            return cartItemsService.create(cartItemsCreateDto);
        }

        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Boolean> removeFromCart(UUID productId, UUID userId) {

        CartDto cartDto = null;

        try {
            cartDto = getOneByUserId(userId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (cartDto != null) {
            return cartItemsService.removeByCartIdAndProductId(cartDto.getId(), productId);
        }

        return CompletableFuture.completedFuture(false);
    }

}

