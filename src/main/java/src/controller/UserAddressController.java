
package src.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import src.config.annotation.ApiPrefixController;
import src.config.annotation.Authenticate;
import src.config.annotation.RequiresAuthorization;
import src.config.dto.PagedResultDto;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;
import src.service.UserAddress.IUserAddressService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/user-addresses")
public class UserAddressController {
    private final IUserAddressService userAddressService;

    public UserAddressController(IUserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }


    @Authenticate
    @RequiresAuthorization("ADMIN")
    @GetMapping("/{id}")
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> findOneById(@PathVariable UUID id) {
        return userAddressService.getOne(id);
    }


    @Authenticate
    @RequiresAuthorization("ADMIN")
    @GetMapping
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<List<UserAddressDto>> findAll() {
        return userAddressService.getAll();
    }

    @GetMapping("/pagination")
    public CompletableFuture<PagedResultDto<UserAddressDto>> findAllPagination(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                               @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                               @RequestParam(required = false, defaultValue = "createAt") String orderBy) {
        return userAddressService.findAllPagination(request, size, page * size);
    }

    @Authenticate
    @RequiresAuthorization("ADMIN")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> create() {
        return null;
    }
    @Authenticate
    @RequiresAuthorization("ADMIN")
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> update(@PathVariable UUID id, UserAddressUpdateDto useraddress) {
        return userAddressService.update(id, useraddress);
    }
    @Authenticate
    @RequiresAuthorization("ADMIN")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Remove")
    public CompletableFuture<Void> remove(@PathVariable UUID id) {
        return userAddressService.remove(id);
    }

    @Authenticate
    @GetMapping(value = "/my-address", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Remove")
    public CompletableFuture<List<UserAddressDto>> getMyAddresses() {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return userAddressService.getMyAddresses(userId);
    }

    @Authenticate
    @PostMapping(value = "/my-address/create", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Remove")
    public CompletableFuture<UserAddressDto> addMyAddress(@RequestBody UserAddressCreateDto input) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return userAddressService.addMyAddress(userId, input);
    }

    //    @Authenticate
//    @GetMapping(value = "/my-address", produces = MediaType.APPLICATION_JSON_VALUE)
//    public CompletableFuture<List<UserAddressDto>> getMyAddresses(UUID id){
//        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
//        return userAddressService.getMyAddresses(userId);
//    }
    @Authenticate
    @PatchMapping(value = "/my-address/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> updateMyAddress(@PathVariable UUID id, @RequestBody UserAddressUpdateDto useraddress) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return userAddressService.updateMyAddress(id, userId, useraddress);
    }

    @Authenticate
    @DeleteMapping(value = "/my-address/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<UserAddressDto> deleteMyAddress(@PathVariable UUID id) {
        UUID userId = ((UUID) (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("id")));
        return userAddressService.deleteMyAddress(id, userId);
    }

}
