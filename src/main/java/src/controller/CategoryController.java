
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.Category.Dtos.CategoryCreateDto;
import src.service.Category.Dtos.CategoryDto;
import src.service.Category.Dtos.CategoryUpdateDto;
import src.service.Category.ICategoryService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/{id}")
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CategoryDto> findOneById(@PathVariable UUID id) {
        return categoryService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<CategoryDto>> findAll() {
        return categoryService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<CategoryDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer skip,
                                                                            @RequestParam(required = false, defaultValue = "10") Integer limit,
                                                                            @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return categoryService.findAllPagination(request, limit, skip);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CategoryDto> create(@RequestBody CategoryCreateDto input) {
        return categoryService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CategoryDto> update(@PathVariable UUID id, @RequestBody CategoryUpdateDto category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return categoryService.remove(id);
    }

    @GetMapping("/features")
    public CompletableFuture<List<CategoryDto>> getCategoryFeatures() {
        return categoryService.getCategoryFeatures();
    }


}
