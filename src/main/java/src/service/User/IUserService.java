
package src.service.User;

import src.service.IService;
import src.service.User.Dtos.UserCreateDto;
import src.service.User.Dtos.UserDto;
import src.service.User.Dtos.UserProfileDto;
import src.service.User.Dtos.UserUpdateDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IUserService extends IService<UserDto, UserCreateDto, UserUpdateDto> {

    public double getDiscountFromUserLevel(UUID id);

    public CompletableFuture<UserProfileDto> getMyProfile(UUID id);
    public CompletableFuture<UserProfileDto> updateMyProfile(UUID id, UserUpdateDto input);
}
