
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.OrderItems.Dtos.OrderItemsCreateDto;
import src.service.OrderItems.Dtos.OrderItemsDto;
import src.service.OrderItems.Dtos.OrderItemsUpdateDto;
import src.service.OrderItems.IOrderItemsService;
import src.service.OrderItems.OrderItemsService;
import src.service.Product.Dtos.ProductDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/orderitemss")
public class OrderItemsController {
    @Autowired
    private IOrderItemsService orderitemsService;


    @GetMapping( "/{id}")
//    @Tag(name = "orderitemss", description = "Operations related to orderitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrderItemsDto> findOneById(@PathVariable UUID id) {
        return orderitemsService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "orderitemss", description = "Operations related to orderitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<OrderItemsDto>> findAll() {
       return orderitemsService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderitemss", description = "Operations related to orderitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrderItemsDto> create(@RequestBody OrderItemsCreateDto input) {
        return orderitemsService.create(input);
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<OrderItemsDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                           @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return orderitemsService.findAllPagination(request, size, page * size);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderitemss", description = "Operations related to orderitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<OrderItemsDto> update(@PathVariable UUID id, OrderItemsUpdateDto orderitems) {
        return orderitemsService.update(id, orderitems);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "orderitemss", description = "Operations related to orderitemss")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return orderitemsService.remove(id);
    }
}
