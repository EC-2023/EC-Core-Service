
package src.service.Orders;

import src.service.IService;
import src.service.Orders.Dtos.OrdersCreateDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersUpdateDto;
import src.service.Orders.Dtos.PayLoadOrder;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IOrdersService extends IService<OrdersDto, OrdersCreateDto, OrdersUpdateDto> {
    public CompletableFuture<List<OrdersDto>> getMyOrders(UUID id);
    public CompletableFuture<OrdersDto> addMyOrder(UUID userId, PayLoadOrder input);
}
