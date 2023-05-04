
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.dto.PagedResultDto;
import src.service.Orders.Dtos.OrdersCreateDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersUpdateDto;
import src.service.Orders.Dtos.PayLoadOrder;
import src.service.Orders.IOrdersService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/orders")
public class OrdersController {
    private final IOrdersService ordersService;

    public OrdersController(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Authenticate
    @GetMapping("/{id}")
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrdersDto> findOneById(@PathVariable UUID id) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return ordersService.getOne(id, userId);
    }

    @Authenticate
    @GetMapping()
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<OrdersDto>> findAll() {
        return ordersService.getAll();
    }

    @Authenticate
    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<OrdersDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                          @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                                          @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return ordersService.findAllPagination(request, limit, skip);
    }

    @Authenticate
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrdersDto> create(@RequestBody OrdersCreateDto input) {
        return ordersService.create(input);
    }


    @Authenticate
    @PostMapping(value = "/add-my-order", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrdersDto> addMyOrder(@RequestBody PayLoadOrder input) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return ordersService.addMyOrder(userId, input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrdersDto> update(@PathVariable UUID id, OrdersUpdateDto orders) {
        return ordersService.update(id, orders);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return ordersService.remove(id);
    }


    @PatchMapping(value = "/{id}/update-confirm")
    @Authenticate
    public CompletableFuture<OrdersDto> acceptOrder(@PathVariable UUID id) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return ordersService.acceptOrder(userId, id);
    }

    @PatchMapping(value = "/{id}/update-cancel")
    @Authenticate
    public CompletableFuture<OrdersDto> cancelOrder(@PathVariable UUID id) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return ordersService.cancelOrder(userId, id);
    }

    @PatchMapping(value = "/{id}/update-delivery")
    @Authenticate
    public CompletableFuture<OrdersDto> confirmDelivery(@PathVariable UUID id) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return ordersService.confirmDelivery(userId, id);
    }

    @PatchMapping(value = "/{id}/cancel-delivery")
    @Authenticate
    public CompletableFuture<OrdersDto> cancelDelivery(@PathVariable UUID id) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return ordersService.cancelDelivery(userId, id);
    }

    @PatchMapping(value = "/{id}/update-success")
    @Authenticate
    public CompletableFuture<OrdersDto> doneOrder(@PathVariable UUID id) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return ordersService.finishOrder(userId, id);
    }


    @Authenticate
    @GetMapping("/getMyOrder")
    public CompletableFuture<PagedResultDto<OrdersDto>> getMyOrder(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                         @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                                         @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return ordersService.getMyOrder(request, limit, skip);
    }
}
