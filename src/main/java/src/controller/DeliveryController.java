
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.Delivery.Dtos.DeliveryCreateDto;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.Delivery.Dtos.DeliveryUpdateDto;
import src.service.Delivery.DeliveryService;
import src.service.Delivery.IDeliveryService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/deliverys")
public class DeliveryController {
    @Autowired
    private IDeliveryService deliveryService;


    @GetMapping( "/{id}")
//    @Tag(name = "deliverys", description = "Operations related to deliverys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<DeliveryDto> findOneById(@PathVariable UUID id) {
        return deliveryService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "deliverys", description = "Operations related to deliverys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<DeliveryDto>> findAll() {
       return deliveryService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<DeliveryDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                             @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                             @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return deliveryService.findAllPagination(request, size, page * size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "deliverys", description = "Operations related to deliverys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<DeliveryDto> create(@RequestBody DeliveryCreateDto input) {
        return deliveryService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "deliverys", description = "Operations related to deliverys")
//    @Operation(summary = "Hello API")
    public CompletableFuture<DeliveryDto> update(@PathVariable UUID id, DeliveryUpdateDto delivery) {
        return deliveryService.update(id, delivery);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "deliverys", description = "Operations related to deliverys")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return deliveryService.remove(id);
    }
}
