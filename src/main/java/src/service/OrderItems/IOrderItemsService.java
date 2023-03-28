
package src.service.OrderItems;

import src.service.IService;
import src.service.OrderItems.Dtos.OrderItemsCreateDto;
import src.service.OrderItems.Dtos.OrderItemsDto;
import src.service.OrderItems.Dtos.OrderItemsUpdateDto;

public interface IOrderItemsService extends IService<OrderItemsDto, OrderItemsCreateDto, OrderItemsUpdateDto> {
}
