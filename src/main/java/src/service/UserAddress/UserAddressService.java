

package src.service.UserAddress;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.UserAddress;
import src.repository.IUserAddressRepository;
import src.service.UserAddress.Dtos.UserAddressCreateDto;
import src.service.UserAddress.Dtos.UserAddressDto;
import src.service.UserAddress.Dtos.UserAddressUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserAddressService implements IUserAddressService {
    private final IUserAddressRepository useraddRepository;
    private final ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    public UserAddressService(IUserAddressRepository useraddRepository, ModelMapper toDto) {
        this.useraddRepository = useraddRepository;
        this.toDto = toDto;
    }

    @Async
    public CompletableFuture<List<UserAddressDto>> getAll() {
        return CompletableFuture.completedFuture(
                useraddRepository.findAll().stream().map(
                        x -> toDto.map(x, UserAddressDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserAddressDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(useraddRepository.findById(id), UserAddressDto.class));
    }

    @Async
    @Override
    public CompletableFuture<UserAddressDto> create(UserAddressCreateDto input) {
        return null;
    }


    @Async
    @Override
    public CompletableFuture<UserAddressDto> update(UUID id, UserAddressUpdateDto useraddress) {
        UserAddress existingUserAddress = useraddRepository.findById(id).orElse(null);
        if (existingUserAddress == null)
            throw new NotFoundException("Unable to find User Address!");
        BeanUtils.copyProperties(useraddress, existingUserAddress);
        existingUserAddress.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(useraddRepository.save(existingUserAddress), UserAddressDto.class));
    }

    @Async
    @Override
    public CompletableFuture<PagedResultDto<UserAddressDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = useraddRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<UserAddress> features = new ApiQuery<>(request, em, UserAddress.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, UserAddressDto.class)).toList()));
    }

    @Async
    @Override
    public CompletableFuture<Void> remove(UUID id) {
        UserAddress existingUserAddress = useraddRepository.findById(id).orElse(null);
        if (existingUserAddress == null)
            throw new NotFoundException("Unable to find User Address!");
        existingUserAddress.setIsDeleted(true);
        existingUserAddress.setUpdateAt(new Date(new java.util.Date().getTime()));
        useraddRepository.save(toDto.map(existingUserAddress, UserAddress.class));
        return CompletableFuture.completedFuture(null);
    }


    @Async
    @Override
    public CompletableFuture<List<UserAddressDto>> getMyAddresses(UUID id) {
        return CompletableFuture.completedFuture(
                useraddRepository.findByUserId(id).stream().map(
                        x -> toDto.map(x, UserAddressDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    @Override
    public CompletableFuture<UserAddressDto> addMyAddress(UUID id, UserAddressCreateDto input) {
        UserAddress userAddress = toDto.map(input, UserAddress.class);
        userAddress.setUserId(id);
        useraddRepository.save(userAddress);
        return CompletableFuture.completedFuture(toDto.map(userAddress, UserAddressDto.class));
    }
}

