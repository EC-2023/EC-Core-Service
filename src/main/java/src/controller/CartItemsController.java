
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.model.CartItems;
import src.service.Cart.Dtos.CartDto;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.Dtos.CartItemsUpdateDto;
import src.service.CartItems.CartItemsService;
import src.service.CartItems.ICartItemsService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/cartitemss")
public class CartItemsController {
    @Autowired
    private ICartItemsService cartitemsService;


    @GetMapping( "/{id}")
//    @Tag(name = "cartitemss", description = "Operations related to cartitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CartItemsDto> findOneById(@PathVariable UUID id) {
        return cartitemsService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "cartitemss", description = "Operations related to cartitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<CartItemsDto>> findAll() {
       return cartitemsService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<CartItemsDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                              @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                              @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return cartitemsService.findAllPagination(request, size, page * size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "cartitemss", description = "Operations related to cartitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CartItemsDto> create(@RequestBody CartItemsCreateDto input) {
        return cartitemsService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "cartitemss", description = "Operations related to cartitemss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CartItemsDto> update(@PathVariable UUID id, CartItemsUpdateDto cartitems) {
        return cartitemsService.update(id, cartitems);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "cartitemss", description = "Operations related to cartitemss")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return cartitemsService.remove(id);
    }
}
