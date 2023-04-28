
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
    CompletableFuture<OrdersDto> acceptOrder(UUID userId, UUID orderId);
    CompletableFuture<OrdersDto> cancelOrder(UUID userId, UUID orderId);
    CompletableFuture<OrdersDto> finishOrder(UUID userId, UUID orderId);
    public CompletableFuture<OrdersDto> getOne(UUID id, UUID userId);
    public CompletableFuture<OrdersDto> confirmDelivery(UUID userId, UUID orderId);
    public CompletableFuture<OrdersDto> cancelDelivery(UUID userId, UUID orderId);
}
