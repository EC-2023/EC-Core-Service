
package src.service.Cart;

import src.service.IService;
import src.service.Cart.Dtos.CartCreateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.Cart.Dtos.CartUpdateDto;

public interface ICartService extends IService<CartDto, CartCreateDto, CartUpdateDto> {
}
