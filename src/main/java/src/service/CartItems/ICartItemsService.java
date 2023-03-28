
package src.service.CartItems;

import src.service.IService;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.Dtos.CartItemsUpdateDto;

public interface ICartItemsService extends IService<CartItemsDto, CartItemsCreateDto, CartItemsUpdateDto> {
}
