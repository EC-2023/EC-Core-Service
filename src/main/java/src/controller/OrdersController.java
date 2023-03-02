
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.Orders.Dtos.OrdersCreateDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersUpdateDto;
import src.service.Orders.OrdersService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/orderss")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;


    @GetMapping( "/{id}")
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrdersDto> findOneById(@PathVariable UUID id) {
        return ordersService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<OrdersDto>> findAll() {
       return ordersService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderss", description = "Operations related to orderss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrdersDto> create(@RequestBody OrdersCreateDto input) {
        return ordersService.create(input);
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
}
