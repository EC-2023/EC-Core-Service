
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.dto.PagedResultDto;
import src.service.Product.Dtos.PayLoadUpdateProduct;
import src.service.Product.Dtos.ProductCreatePayload;
import src.service.Product.Dtos.ProductDetailDto;
import src.service.Product.Dtos.ProductDto;
import src.service.Product.IProductService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }


//    @GetMapping("/{id}")
////    @Tag(name = "products", description = "Operations related to products")
////    @Operation(summary = "Hello API")
//    public CompletableFuture<ProductDto> findOneById(@PathVariable UUID id) {
//        return productService.getOne(id);
//    }


    @GetMapping("/{id}")
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductDetailDto> getDetailProduct(@PathVariable UUID id) {
        return productService.getDetailProduct(id);
    }

    @GetMapping()
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<ProductDto>> findAll() {
        return productService.getAll();
    }


    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<ProductDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                           @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                           @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return productService.findAllPagination(request, size, page * size);
    }

    @Authenticate
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductDto> create(@RequestBody ProductCreatePayload input) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));

        return productService.create(userId, input);
    }

    @Authenticate
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductCreatePayload> update(@PathVariable UUID id, @RequestBody PayLoadUpdateProduct product) throws InvocationTargetException, IllegalAccessException {
        return productService.update(id, product);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return productService.remove(id);
    }


    @Authenticate
    @PatchMapping(value = "/{id}/update-quantity", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ProductDto> updateQuantity(@PathVariable("id") UUID productId,
                                                        @RequestParam int quantity) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return productService.updateQuantity(userId, productId, quantity);
    }

    @Authenticate
    @PatchMapping(value = "/{id}/update-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ProductDto> updateStatusProduct(@PathVariable("id") UUID productId,
                                                             @RequestParam boolean status) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return productService.updateStatusProduct(userId, productId, status);
    }

    @Authenticate
    @PatchMapping(value = "/{id}/update-active", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ProductDto> updateActiveProduct(@PathVariable("id") UUID productId,
                                                             @RequestParam boolean status) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return productService.updateActiveProduct(userId, productId, status);
    }

    @GetMapping(value = "/search/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<List<String>> findCategoryNamesAndProductNamesByKeyword(@PathVariable String keyword) {
        return productService.findCategoryNamesAndProductNamesByKeyword(keyword);
    }
}
