
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.Store.Dtos.StoreCreateDto;
import src.service.Store.Dtos.StoreDto;
import src.service.Store.Dtos.StoreUpdateDto;
import src.service.Store.StoreService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;


    @GetMapping( "/{id}")
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "stores", description = "Operations related to stores")
//    @Operation(summary = "Hello API")
    public CompletableFuture<StoreDto> create(@RequestBody StoreCreateDto input) {
        return storeService.create(input);
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
}
