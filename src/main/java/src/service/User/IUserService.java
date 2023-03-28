
package src.service.User;

import src.service.IService;
import src.service.User.Dtos.UserCreateDto;
import src.service.User.Dtos.UserDto;
import src.service.User.Dtos.UserUpdateDto;

public interface IUserService extends IService<UserDto, UserCreateDto, UserUpdateDto> {
}
