
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.dto.PagedResultDto;
import src.service.Cart.Dtos.CartCreateDto;
import src.service.Cart.Dtos.CartDto;
import src.service.Cart.Dtos.CartUpdateDto;
import src.service.Cart.CartService;
import src.service.Cart.ICartService;
import src.service.CartItems.Dtos.CartItemsCreateDto;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.Product.Dtos.ProductDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@ApiPrefixController(value = "/carts")
public class CartController {
    @Autowired
    private ICartService cartService;


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

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<CartDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page ,
                                                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                           @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return cartService.findAllPagination(request, size, page * size);
    }

    @Authenticate
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "carts", description = "Operations related to carts")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CartDto> create() {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return cartService.create(userId);
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

    @Authenticate
    @PostMapping(value = "/add",produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<CartItemsDto> addToCart(@RequestBody CartItemsCreateDto input) throws ExecutionException, InterruptedException {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return cartService.addToCart(input, userId);
    }

    @Authenticate
    @DeleteMapping(value = "/remove/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<Boolean> removeFromCart(@PathVariable UUID productId) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return cartService.removeFromCart(productId, userId);
    }
}
