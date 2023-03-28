
package src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;
import src.service.UserAddress.UserAddressService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@ApiPrefixController(value = "/useraddresss")
public class UserAddressController {
    @Autowired
    private UserAddressService useraddressService;


    @GetMapping( "/{id}")
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "useraddresss", description = "Operations related to useraddresss")
//    @Operation(summary = "Hello API")
    public CompletableFuture<UserAddressDto> create(@RequestBody UserAddressCreateDto input) {
        return useraddressService.create(input);
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

    // tìm kiếm địa chỉ người dùng theo city
    @GetMapping("/search/city/{city}")
    public CompletableFuture<List<UserAddressDto>> searchByCity(@RequestParam String city) {
        return useraddressService.findByCity(city);
    }

    // tìm kiếm địa chỉ người dùng theo country
    @GetMapping("/search/country/{country}")
    public CompletableFuture<List<UserAddressDto>> searchByCountry(@RequestParam String country) {
        return useraddressService.findByCountry(country);
    }

    // sắp xếp theo User Address theo city
    @GetMapping("/sort-city")
    public CompletableFuture<List<UserAddressDto>> getAllSortedByCiTy() {
        return useraddressService.getAllSortedByCiTy();
    }
    // sắp xếp theo User Address theo country
    @GetMapping("/sort-country")
    public CompletableFuture<List<UserAddressDto>> getAllSortedByCountry() {
        return useraddressService.getAllSortedByCountry();
    }

}
