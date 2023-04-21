

package src.service.Commission;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.config.utils.MapperUtils;
import src.model.Commission;
import src.repository.ICommissionRepository;
import src.service.Commission.Dtos.CommissionCreateDto;
import src.service.Commission.Dtos.CommissionDto;
import src.service.Commission.Dtos.CommissionUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CommissionService implements ICommissionService {
    private final ICommissionRepository commissionRepository;
    private final ModelMapper toDto;
    @PersistenceContext
    EntityManager em;

    public CommissionService(ICommissionRepository commissionRepository, ModelMapper toDto) {
        this.commissionRepository = commissionRepository;
        this.toDto = toDto;
    }


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
//        BeanUtils.copyProperties(commissions, existingCommission);
        MapperUtils.toDto(commissions, existingCommission);
        existingCommission.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(commissionRepository.save(existingCommission), CommissionDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<CommissionDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        Pagination pagination = Pagination.create(0, skip, limit);
        ApiQuery<Commission> features = new ApiQuery<>(request, em, Commission.class, pagination);
        pagination.setTotal(features.filter().orderBy().exec().size());
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, CommissionDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Commission existingCommission = commissionRepository.findById(id).orElse(null);
        if (existingCommission == null)
            throw new NotFoundException("Unable to find commission!");
        existingCommission.setIsDeleted(true);
        commissionRepository.save(toDto.map(existingCommission, Commission.class));
        return CompletableFuture.completedFuture(null);
    }

}

