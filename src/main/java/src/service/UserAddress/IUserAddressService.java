
package src.service.UserAddress;

import src.service.IService;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IUserAddressService extends IService<UserAddressDto, UserAddressCreateDto, UserAddressUpdateDto> {
    CompletableFuture<List<UserAddressDto>> getMyAddresses(UUID id);
    CompletableFuture<UserAddressDto> addMyAddress(UUID id, UserAddressCreateDto input);
}
