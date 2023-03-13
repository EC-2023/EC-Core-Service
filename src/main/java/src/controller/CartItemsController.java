
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.CartItems.CartItemsService;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.CartItems.Dtos.CartItemsUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/cartitemss")
public class CartItemsController {
    @Autowired
    private CartItemsService cartitemsService;


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