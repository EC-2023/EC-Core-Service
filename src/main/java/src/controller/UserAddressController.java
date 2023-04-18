
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.dto.PagedResultDto;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;
import src.service.UserAddress.IUserAddressService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/useraddresss")
public class UserAddressController {
    @Autowired
    private IUserAddressService useraddressService;


    @GetMapping("/{id}")
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> findOneById(@PathVariable UUID id) {
        return useraddressService.getOne(id);
    }

    @GetMapping()
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<UserAddressDto>> findAll() {
        return useraddressService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<UserAddressDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                               @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                               @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return useraddressService.findAllPagination(request, size, page * size);
    }

    @Authenticate
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> create() {
       return null;
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> update(@PathVariable UUID id, UserAddressUpdateDto useraddress) {
        return useraddressService.update(id, useraddress);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return useraddressService.remove(id);
    }

    @GetMapping(value = "/my-address", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Remove")
    public CompletableFuture<List<UserAddressDto>> getMyAddresses() {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return useraddressService.getMyAddresses(userId);
    }

    @PostMapping(value = "my-address/create", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Remove")
    public CompletableFuture<UserAddressDto> addMyAddress(@RequestBody UserAddressCreateDto input) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return useraddressService.addMyAddress(userId, input);
    }

}
