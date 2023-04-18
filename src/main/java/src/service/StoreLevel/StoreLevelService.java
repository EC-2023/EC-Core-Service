

package src.service.StoreLevel;

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
import src.model.Product;
import src.model.StoreLevel;
import src.repository.IStoreLevelRepository;
import src.service.Product.Dtos.ProductDto;
import src.service.StoreLevel.Dtos.StoreLevelCreateDto;
import src.service.StoreLevel.Dtos.StoreLevelDto;
import src.service.StoreLevel.Dtos.StoreLevelUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class StoreLevelService implements IStoreLevelService {
    @Autowired
    private IStoreLevelRepository storelevelRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<StoreLevelDto>> getAll() {
        return CompletableFuture.completedFuture(
                storelevelRepository.findAll().stream().map(
                        x -> toDto.map(x, StoreLevelDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<StoreLevelDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(storelevelRepository.findById(id).get(), StoreLevelDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<StoreLevelDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = storelevelRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<StoreLevel> features = new ApiQuery<>(request, em, StoreLevel.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, StoreLevelDto.class)).toList()));
    }

    @Async
    public CompletableFuture<StoreLevelDto> create(StoreLevelCreateDto input) {
        StoreLevel storelevel = storelevelRepository.save(toDto.map(input, StoreLevel.class));
        return CompletableFuture.completedFuture(toDto.map(storelevelRepository.save(storelevel), StoreLevelDto.class));
    }

    @Async
    public CompletableFuture<StoreLevelDto> update(UUID id, StoreLevelUpdateDto storelevel) {
        StoreLevel existingStoreLevel = storelevelRepository.findById(id).orElse(null);
        if (existingStoreLevel == null)
            throw new NotFoundException("Unable to find store level!");
        BeanUtils.copyProperties(storelevel, existingStoreLevel);
        existingStoreLevel.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(storelevelRepository.save(existingStoreLevel), StoreLevelDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        StoreLevel existingStoreLevel = storelevelRepository.findById(id).orElse(null);
        if (existingStoreLevel == null)
            throw new NotFoundException("Unable to find store level!");
        existingStoreLevel.setIsDeleted(true);
        existingStoreLevel.setUpdateAt(new Date(new java.util.Date().getTime()));
        storelevelRepository.save(existingStoreLevel);
        return CompletableFuture.completedFuture(null);
    }
}

