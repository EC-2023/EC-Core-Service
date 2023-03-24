

package src.service.CartItems;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.exception.NotFoundException;
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

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CartItemsService {
    @Autowired
    private ICartItemsRepository cartitemsRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<CartItemsDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<CartItemsDto>) cartitemsRepository.findAll().stream().map(
                        x -> toDto.map(x, CartItemsDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<CartItemsDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(cartitemsRepository.findById(id).get(), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<CartItemsDto> create(CartItemsCreateDto input) {
        CartItems cartitems = cartitemsRepository.save(toDto.map(input, CartItems.class));
        return CompletableFuture.completedFuture(toDto.map(cartitemsRepository.save(cartitems), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<CartItemsDto> update(UUID id, CartItemsUpdateDto cartitems) {
        CartItems existingCartItems = cartitemsRepository.findById(id).orElse(null);
        if (existingCartItems == null)
            throw new NotFoundException("Unable to find cart items!");
        Date createAt = existingCartItems.getCreateAt();
        existingCartItems = toDto.map(cartitems, CartItems.class);
        existingCartItems.setId(id);
        existingCartItems.setCreateAt(createAt);
        return CompletableFuture.completedFuture(toDto.map(cartitemsRepository.save(existingCartItems), CartItemsDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        CartItems existingCartItems = cartitemsRepository.findById(id).orElse(null);
        if (existingCartItems == null)
            throw new NotFoundException("Unable to find cart items!");
        existingCartItems.setIsDeleted(true);
        cartitemsRepository.save(toDto.map(existingCartItems, CartItems.class));
        return CompletableFuture.completedFuture(null);
    }
}

