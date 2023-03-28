
package src.service.Orders;

import src.service.IService;
import src.service.Orders.Dtos.OrdersCreateDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersUpdateDto;

public interface IOrdersService extends IService<OrdersDto, OrdersCreateDto, OrdersUpdateDto> {
}
