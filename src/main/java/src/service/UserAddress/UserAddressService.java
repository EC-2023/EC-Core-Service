

package src.service.UserAddress;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
import src.model.UserAddress;
import src.repository.IUserAddressRepository;;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;

import java.util.*;
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
        UserAddress existinguseraddress = useraddressRepository.findById(id).orElse(null);
        if (existinguseraddress == null)
            throw new NotFoundException("Unable to find User Address!");
        Date createAt = existinguseraddress.getCreateAt();
        existinguseraddress = toDto.map(useraddress, UserAddress.class);
        existinguseraddress.setId(id);
        existinguseraddress.setCreateAt(createAt);
        return CompletableFuture.completedFuture(toDto.map(useraddressRepository.save(existinguseraddress), UserAddressDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserAddress existinguseraddress = useraddressRepository.findById(id).orElse(null);
        if (existinguseraddress == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existinguseraddress.setIsDeleted(true);
        useraddressRepository.save(toDto.map(existinguseraddress, UserAddress.class));
        return CompletableFuture.completedFuture(null);
    }

    // tìm kiếm địa chỉ người dùng theo city
    @Async
    public CompletableFuture<List<UserAddressDto>> findByCity(String city) {
        Collection<Object> userAddresses = useraddressRepository.findByCityContainingIgnoreCase(city);
        if (userAddresses == null || userAddresses.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find city: " + city);
        }
        List<UserAddressDto> userAddressDtos = userAddresses.stream()
                .map(userAddress -> toDto.map(userAddress, UserAddressDto.class))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(userAddressDtos);
    }
    // tìm kiếm địa chỉ người dùng theo country
    @Async
    public CompletableFuture<List<UserAddressDto>> findByCountry(String country) {
        Collection<Object> userAddresses = useraddressRepository.findByCountryContainingIgnoreCase(country);
        if (userAddresses == null || userAddresses.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find country: " + country);
        }
        List<UserAddressDto> userAddressDtos = userAddresses.stream()
                .map(userAddress -> toDto.map(userAddress, UserAddressDto.class))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(userAddressDtos);
    }

    // sắp xếp theo địa chỉ người dùng theo city
    @Async
    public CompletableFuture<List<UserAddressDto>> getAllSortedByCiTy() {
        List<UserAddressDto> useraddressDtos = useraddressRepository.findAll().stream()
                .map(useraddress -> toDto.map(useraddress, UserAddressDto.class))
                .sorted(Comparator.comparing(UserAddressDto::getCity))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(useraddressDtos);
    }

    // sắp xếp theo địa chỉ người dùng theo country
    @Async
    public CompletableFuture<List<UserAddressDto>> getAllSortedByCountry() {
        List<UserAddressDto> useraddressDtos = useraddressRepository.findAll().stream()
                .map(useraddress -> toDto.map(useraddress, UserAddressDto.class))
                .sorted(Comparator.comparing(UserAddressDto::getCountry))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(useraddressDtos);
    }


}

