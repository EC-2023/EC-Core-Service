
package src.service.CartItems;

import src.service.IService;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.Dtos.CartItemsUpdateDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ICartItemsService extends IService<CartItemsDto, CartItemsCreateDto, CartItemsUpdateDto> {

    CompletableFuture<Boolean> removeByCartIdAndProductId(UUID cartId, UUID productId);

}
