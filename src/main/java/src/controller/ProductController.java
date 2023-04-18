
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.Product.Dtos.ProductCreateDto;
import src.service.Product.Dtos.ProductDto;
import src.service.Product.Dtos.ProductUpdateDto;
import src.service.Product.IProductService;
import src.service.Product.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/products")
public class ProductController {
    @Autowired
    private IProductService productService;


    @GetMapping( "/{id}")
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductDto> findOneById(@PathVariable UUID id) {
        return productService.getOne(id);
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductDto> create(@RequestBody ProductCreateDto input) {
        return productService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductDto> update(@PathVariable UUID id, ProductUpdateDto product) {
        return productService.update(id, product);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "products", description = "Operations related to products")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return productService.remove(id);
    }
}
