

package src.service.Cart;

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
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.Cart;
import src.model.Cart;
import src.repository.ICartRepository;
import src.service.Cart.Dtos.CartCreateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.Cart.Dtos.CartUpdateDto;
import src.service.Cart.Dtos.CartDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private ModelMapper toDto;

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
        return CompletableFuture.completedFuture(toDto.map(cartRepository.findById(id).get(), CartDto.class));
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
        Cart cart = cartRepository.save(toDto.map(input, Cart.class));
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
}

