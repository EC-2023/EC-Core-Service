
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.Attribute.Dtos.AttributeDto;
import src.service.AttributeValue.AttributeValueService;
import src.service.AttributeValue.Dtos.AttributeValueCreateDto;
import src.service.AttributeValue.Dtos.AttributeValueDto;
import src.service.AttributeValue.Dtos.AttributeValueUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/attributevalues")
public class AttributeValueController {
    @Autowired
    private AttributeValueService attributevalueService;


    @GetMapping( "/{id}")
//    @Tag(name = "attributevalues", description = "Operations related to attributevalues")
//    @Operation(summary = "Hello API")
    public CompletableFuture<AttributeValueDto> findOneById(@PathVariable UUID id) {
        return attributevalueService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "attributevalues", description = "Operations related to attributevalues")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<AttributeValueDto>> findAll() {
        return attributevalueService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<AttributeValueDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page ,
                                                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                                  @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return attributevalueService.findAllPagination(request, size, page * size);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "attributevalues", description = "Operations related to attributevalues")
//    @Operation(summary = "Hello API")
    public CompletableFuture<AttributeValueDto> create(@RequestBody AttributeValueCreateDto input) {
        return attributevalueService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "attributevalues", description = "Operations related to attributevalues")
//    @Operation(summary = "Hello API")
    public CompletableFuture<AttributeValueDto> update(@PathVariable UUID id, AttributeValueUpdateDto attributevalue) {
        return attributevalueService.update(id, attributevalue);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "attributevalues", description = "Operations related to attributevalues")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return attributevalueService.remove(id);
    }


}
