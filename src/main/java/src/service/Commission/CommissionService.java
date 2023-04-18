

package src.service.Commission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.exception.NotFoundException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.utils.ApiQuery;
import src.model.Commission;
import src.repository.ICommissionRepository;
import src.service.Commission.Dtos.CommissionCreateDto;
import src.service.Commission.Dtos.CommissionDto;
import src.service.Commission.Dtos.CommissionUpdateDto;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CommissionService {
    @Autowired
    private ICommissionRepository commissionRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;


    @Async
    public CompletableFuture<List<CommissionDto>> getAll() {
        return CompletableFuture.completedFuture(
                commissionRepository.findAll().stream().map(
                        x -> toDto.map(x, CommissionDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<CommissionDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(commissionRepository.findById(id), CommissionDto.class));
    }

    @Async
    public CompletableFuture<CommissionDto> create(CommissionCreateDto input) {
        Commission commission = commissionRepository.save(toDto.map(input, Commission.class));
        return CompletableFuture.completedFuture(toDto.map(commissionRepository.save(commission), CommissionDto.class));
    }

    @Async
    public CompletableFuture<CommissionDto> update(UUID id, CommissionUpdateDto commissions) {
        Commission existingCommission = commissionRepository.findById(id).orElse(null);
        if (existingCommission == null)
            throw new NotFoundException("Unable to find commission!");
        BeanUtils.copyProperties(commissions, existingCommission);
        existingCommission.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(commissionRepository.save(existingCommission), CommissionDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<CommissionDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = commissionRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<Commission> features = new ApiQuery<>(request, em, Commission.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, CommissionDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Commission existingCommission = commissionRepository.findById(id).orElse(null);
        if (existingCommission == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find commission!");
        existingCommission.setIsDeleted(true);
        existingCommission.setUpdateAt(new Date(new java.util.Date().getTime()));
        commissionRepository.save(toDto.map(existingCommission, Commission.class));
        return CompletableFuture.completedFuture(null);
    }

}

