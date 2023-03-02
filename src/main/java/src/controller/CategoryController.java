
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.Category.Dtos.CategoryCreateDto;
import src.service.Category.Dtos.CategoryDto;
import src.service.Category.Dtos.CategoryUpdateDto;
import src.service.Category.CategoryService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping( "/{id}")
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CategoryDto> create(@RequestBody CategoryCreateDto input) {
        return categoryService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CategoryDto> update(@PathVariable UUID id, CategoryUpdateDto category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "categorys", description = "Operations related to categorys")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return categoryService.remove(id);
    }
}
