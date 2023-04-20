
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.service.Commission.Dtos.CommissionCreateDto;
import src.service.Commission.Dtos.CommissionDto;
import src.service.Commission.Dtos.CommissionUpdateDto;
import src.service.Commission.ICommissionService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/commissions")
public class CommissionController {
    private final ICommissionService commissionService;

    public CommissionController(ICommissionService commissionService) {
        this.commissionService = commissionService;
    }


    @GetMapping( "/{id}")
//    @Tag(name = "commissions", description = "Operations related to commissions")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CommissionDto> findOneById(@PathVariable UUID id) {
        return commissionService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "commissions", description = "Operations related to commissions")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<CommissionDto>> findAll() {
        return commissionService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<CommissionDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page ,
                                                                              @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                              @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return commissionService.findAllPagination(request, size, page * size);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "commissions", description = "Operations related to commissions")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CommissionDto> create(@RequestBody CommissionCreateDto input) {
        return commissionService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "commissions", description = "Operations related to commissions")
//    @Operation(summary = "Hello API")
    public CompletableFuture<CommissionDto> update(@PathVariable UUID id,@RequestBody CommissionUpdateDto commission) {
        return commissionService.update(id, commission);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "commissions", description = "Operations related to commissions")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return commissionService.remove(id);
    }


}
