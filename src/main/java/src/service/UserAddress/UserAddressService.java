

package src.service.UserAddress;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.UserAddress;
import src.repository.IUserAddressRepository;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserAddressService {
    @Autowired
    private IUserAddressRepository useraddressRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<UserAddressDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<UserAddressDto>) useraddressRepository.findAll().stream().map(
                        x -> toDto.map(x, UserAddressDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserAddressDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(useraddressRepository.findById(id), UserAddressDto.class));
    }

    @Async
    public CompletableFuture<UserAddressDto> create(UserAddressCreateDto input) {
        UserAddress useraddress = useraddressRepository.save(toDto.map(input, UserAddress.class));
        return CompletableFuture.completedFuture(toDto.map(useraddressRepository.save(useraddress), UserAddressDto.class));
    }

    @Async
    public CompletableFuture<UserAddressDto> update(UUID id, UserAddressUpdateDto useraddress) {
        UserAddress existingUserAddress = useraddressRepository.findById(id).orElse(null);
        if (existingUserAddress == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(useraddressRepository.save(toDto.map(useraddress, UserAddress.class)), UserAddressDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserAddress existingUserAddress = useraddressRepository.findById(id).orElse(null);
        if (existingUserAddress == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingUserAddress.setIsDeleted(true);
        useraddressRepository.save(toDto.map(existingUserAddress, UserAddress.class));
        return CompletableFuture.completedFuture(null);
    }
}

