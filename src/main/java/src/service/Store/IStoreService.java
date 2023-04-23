
package src.service.Store;

import jakarta.servlet.http.HttpServletRequest;
import src.config.dto.PagedResultDto;
import src.service.IService;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Store.Dtos.StoreCreateDto;
import src.service.Store.Dtos.StoreDto;
import src.service.Store.Dtos.StoreUpdateDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IStoreService extends IService<StoreDto, StoreCreateDto, StoreUpdateDto> {
    public CompletableFuture<PagedResultDto<OrdersDto>> findOrderByStore(HttpServletRequest request, Integer limit, Integer skip);
    public CompletableFuture<StoreDto> setActiveStore(UUID storeId, boolean status);
}
