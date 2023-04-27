
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.annotation.RequiresAuthorization;
import src.config.dto.PagedResultDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Product.Dtos.ProductStoreDto;
import src.service.Store.Dtos.StoreCreateDto;
import src.service.Store.Dtos.StoreDto;
import src.service.Store.Dtos.StoreUpdateDto;
import src.service.Store.IStoreService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/stores")
public class StoreController {
    private final IStoreService storeService;

    public StoreController(IStoreService storeService) {
        this.storeService = storeService;
    }


    @GetMapping("/{id}")
//    @Tag(name = "stores", description = "Operations related to stores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<StoreDto> findOneById(@PathVariable UUID id) {
        return storeService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "stores", description = "Operations related to stores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<StoreDto>> findAll() {
        return storeService.getAll();
    }

    @Authenticate
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "stores", description = "Operations related to stores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<StoreDto> create(@RequestBody StoreCreateDto input) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));

        return storeService.create(input, userId);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "stores", description = "Operations related to stores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<StoreDto> update(@PathVariable UUID id, StoreUpdateDto store) {
        return storeService.update(id, store);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "stores", description = "Operations related to stores")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return storeService.remove(id);
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<StoreDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                         @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                                         @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return storeService.findAllPagination(request, limit, skip);
    }

    @Authenticate
    @GetMapping("/getOrdersByMyStore")
    public CompletableFuture<PagedResultDto<OrdersDto>> findOrderByStore(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                         @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                                         @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return storeService.findOrderByStore(request, limit, skip);
    }

    @Authenticate
    @GetMapping("/getProductsByMyStore")
    public CompletableFuture<PagedResultDto<ProductStoreDto>> findProductByStore(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                                 @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                                                 @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return storeService.findProductByStore(request, limit, skip);
    }

    @Authenticate
    @RequiresAuthorization("ADMIN")
    @GetMapping("/{id}/set-active-store")
    public CompletableFuture<StoreDto> setActiveStore(@PathVariable UUID id, @RequestParam boolean status) {
        return storeService.setActiveStore(id, status);
    }

}
