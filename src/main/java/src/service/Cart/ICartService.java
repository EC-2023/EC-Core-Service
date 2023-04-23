
package src.service.Cart;

import src.service.Cart.Dtos.CartCreateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.Cart.Dtos.CartUpdateDto;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.IService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ICartService extends IService<CartDto, CartCreateDto, CartUpdateDto> {
    CompletableFuture<CartDto> getOneByUserId(UUID userId);
    CompletableFuture<CartDto> create(UUID userId) ;
    CompletableFuture<CartItemsDto> addToCart(CartItemsCreateDto input, UUID userId) throws ExecutionException, InterruptedException;
    CompletableFuture<Boolean> removeFromCart(UUID productId, UUID userId);
    public CompletableFuture<List<CartDto>> getMyCarts(UUID userId);
}
