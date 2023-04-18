
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.OrderItems.Dtos.OrderItemsDto;
import src.service.StoreLevel.Dtos.StoreLevelCreateDto;
import src.service.StoreLevel.Dtos.StoreLevelDto;
import src.service.StoreLevel.Dtos.StoreLevelUpdateDto;
import src.service.StoreLevel.IStoreLevelService;
import src.service.StoreLevel.StoreLevelService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/storelevels")
public class StoreLevelController {
    @Autowired
    private IStoreLevelService storelevelService;


    @GetMapping( "/{id}")
//    @Tag(name = "storelevels", description = "Operations related to storelevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<StoreLevelDto> findOneById(@PathVariable UUID id) {
        return storelevelService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "storelevels", description = "Operations related to storelevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<StoreLevelDto>> findAll() {
       return storelevelService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<StoreLevelDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                              @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                              @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return storelevelService.findAllPagination(request, size, page * size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "storelevels", description = "Operations related to storelevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<StoreLevelDto> create(@RequestBody StoreLevelCreateDto input) {
        return storelevelService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "storelevels", description = "Operations related to storelevels")
//    @Operation(summary = "Hello API")
    public CompletableFuture<StoreLevelDto> update(@PathVariable UUID id, StoreLevelUpdateDto storelevel) {
        return storelevelService.update(id, storelevel);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "storelevels", description = "Operations related to storelevels")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return storelevelService.remove(id);
    }
}
