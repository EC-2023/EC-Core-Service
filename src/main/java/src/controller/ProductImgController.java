
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.ProductImg.Dtos.ProductImgCreateDto;
import src.service.ProductImg.Dtos.ProductImgDto;
import src.service.ProductImg.Dtos.ProductImgUpdateDto;
import src.service.ProductImg.IProductImgService;
import src.service.ProductImg.ProductImgService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/productimgs")
public class ProductImgController {
    @Autowired
    private IProductImgService productimgService;


    @GetMapping( "/{id}")
//    @Tag(name = "productimgs", description = "Operations related to productimgs")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductImgDto> findOneById(@PathVariable UUID id) {
        return productimgService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "productimgs", description = "Operations related to productimgs")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<ProductImgDto>> findAll() {
       return productimgService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<ProductImgDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                            @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                            @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return productimgService.findAllPagination(request, size, page * size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "productimgs", description = "Operations related to productimgs")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductImgDto> create(@RequestBody ProductImgCreateDto input) {
        return productimgService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "productimgs", description = "Operations related to productimgs")
//    @Operation(summary = "Hello API")
    public CompletableFuture<ProductImgDto> update(@PathVariable UUID id, ProductImgUpdateDto productimg) {
        return productimgService.update(id, productimg);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "productimgs", description = "Operations related to productimgs")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return productimgService.remove(id);
    }
}
