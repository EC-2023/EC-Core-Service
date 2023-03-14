
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.Role.Dtos.RoleCreateDto;
import src.service.Role.Dtos.RoleDto;
import src.service.Role.Dtos.RoleUpdateDto;
import src.service.Role.RoleService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @GetMapping( "/{id}")
//    @Tag(name = "roles", description = "Operations related to roles")
//    @Operation(summary = "Hello API")
    public CompletableFuture<RoleDto> findOneById(@PathVariable UUID id) {
        return roleService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "roles", description = "Operations related to roles")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<RoleDto>> findAll() {
       return roleService.getAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "roles", description = "Operations related to roles")
//    @Operation(summary = "Hello API")
    public CompletableFuture<RoleDto> create(@RequestBody RoleCreateDto input) {
        return roleService.create(input);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "roles", description = "Operations related to roles")
//    @Operation(summary = "Hello API")
    public CompletableFuture<RoleDto> update(@PathVariable UUID id, RoleUpdateDto role) {
        return roleService.update(id, role);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "roles", description = "Operations related to roles")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return roleService.remove(id);
    }
}
