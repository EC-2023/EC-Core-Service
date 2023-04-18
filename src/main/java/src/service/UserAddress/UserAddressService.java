

package src.service.UserAddress;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;

import src.model.UserAddress;

import src.repository.IUserAddressRepository;
import src.service.UserAddress.Dtos.UserAddressDto;

import src.service.UserAddress.Dtos.UserAddressUpdateDto;

import java.util.Date;
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

    @PersistenceContext
    EntityManager em;

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
    public CompletableFuture<UserAddressDto> create(UUID userid) {
        UserAddress useraddress = new UserAddress();
        useraddress.setUserId(userid);
        return CompletableFuture.completedFuture(toDto.map(useraddressRepository.save(useraddress), UserAddressDto.class));
    }

    @Async
    public CompletableFuture<UserAddressDto> update(UUID id, UserAddressUpdateDto useraddress) {
        UserAddress existingUserAddress = useraddressRepository.findById(id).orElse(null);
        if (existingUserAddress == null)
            throw new NotFoundException("Unable to find User Address!");
        BeanUtils.copyProperties(useraddress, existingUserAddress);
        existingUserAddress.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(useraddressRepository.save(existingUserAddress), UserAddressDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<UserAddressDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = useraddressRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<UserAddress> features = new ApiQuery<>(request, em, UserAddress.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, UserAddressDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        UserAddress existingUserAddress = useraddressRepository.findById(id).orElse(null);
        if (existingUserAddress == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find User Address!");
        existingUserAddress.setIsDeleted(true);
        existingUserAddress.setUpdateAt(new Date(new java.util.Date().getTime()));
        useraddressRepository.save(toDto.map(existingUserAddress, UserAddress.class));
        return CompletableFuture.completedFuture(null);
    }




}

