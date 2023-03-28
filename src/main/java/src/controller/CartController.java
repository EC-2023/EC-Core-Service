
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.Cart.Dtos.CartCreateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.Cart.Dtos.CartUpdateDto;
import src.service.Cart.CartService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/carts")
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping( "/{id}")
//    @Tag(name = "carts", description = "Operations related to carts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CartDto> findOneById(@PathVariable UUID id) {
        return cartService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "carts", description = "Operations related to carts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<CartDto>> findAll() {
       return cartService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "carts", description = "Operations related to carts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CartDto> create(@RequestBody CartCreateDto input) {
        return cartService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "carts", description = "Operations related to carts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CartDto> update(@PathVariable UUID id, CartUpdateDto cart) {
        return cartService.update(id, cart);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "carts", description = "Operations related to carts")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return cartService.remove(id);
    }
}
