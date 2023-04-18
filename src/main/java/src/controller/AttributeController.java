
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.Attribute.AttributeService;
import src.service.Attribute.Dtos.AttributeCreateDto;
import src.service.Attribute.Dtos.AttributeDto;
import src.service.Attribute.Dtos.AttributeUpdateDto;
import src.service.UserLevel.Dtos.UserLevelDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/attributes")
public class AttributeController {
    @Autowired
    private AttributeService attributeService;


    @GetMapping( "/{id}")
//    @Tag(name = "attributes", description = "Operations related to attributes")
//    @Operation(summary = "Hello API")
    public CompletableFuture<AttributeDto> findOneById(@PathVariable UUID id) {
        return attributeService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "attributes", description = "Operations related to attributes")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<AttributeDto>> findAll() {
        return attributeService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<AttributeDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page ,
                                                                             @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                             @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return attributeService.findAllPagination(request, size, page * size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "attributes", description = "Operations related to attributes")
//    @Operation(summary = "Hello API")
    public CompletableFuture<AttributeDto> create(@RequestBody AttributeCreateDto input) {
        return attributeService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "attributes", description = "Operations related to attributes")
//    @Operation(summary = "Hello API")
    public CompletableFuture<AttributeDto> update(@PathVariable UUID id, AttributeUpdateDto attribute) {
        return attributeService.update(id, attribute);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "attributes", description = "Operations related to attributes")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return attributeService.remove(id);
    }



}
