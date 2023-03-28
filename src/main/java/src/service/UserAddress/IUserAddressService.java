
package src.service.UserAddress;

import src.service.IService;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;

public interface IUserAddressService extends IService<UserAddressDto, UserAddressCreateDto, UserAddressUpdateDto> {
}
