

package src.service.Store;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.utils.ApiQuery;
import src.model.Store;
import src.repository.IStoreRepository;
import src.service.Store.Dtos.StoreCreateDto;
import src.service.Store.Dtos.StoreDto;
import src.service.Store.Dtos.StoreUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class StoreService implements IStoreService{
    private final IStoreRepository storeRepository;
    private final ModelMapper toDto;
    @PersistenceContext
    EntityManager em;
    public StoreService(IStoreRepository storeRepository, ModelMapper toDto) {
        this.storeRepository = storeRepository;
        this.toDto = toDto;
    }

    @Async
    public CompletableFuture<List<StoreDto>> getAll() {
        return CompletableFuture.completedFuture(
                storeRepository.findAll().stream().map(
                        x -> toDto.map(x, StoreDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<StoreDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(storeRepository.findById(id), StoreDto.class));
    }

    @Async
    public CompletableFuture<StoreDto> create(StoreCreateDto input) {
        Store store = storeRepository.save(toDto.map(input, Store.class));
        return CompletableFuture.completedFuture(toDto.map(storeRepository.save(store), StoreDto.class));
    }

    @Async
    public CompletableFuture<StoreDto> update(UUID id, StoreUpdateDto store) {
        Store existingStore = storeRepository.findById(id).orElse(null);
        if (existingStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(storeRepository.save(toDto.map(store, Store.class)), StoreDto.class));
    }

    @Override
    @Async
    public CompletableFuture<PagedResultDto<StoreDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        Pagination pagination = Pagination.create(0, skip, limit);
        ApiQuery<Store> features = new ApiQuery<>(request, em, Store.class, pagination);
        pagination.setTotal(features.filter().orderBy().exec().size());
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, StoreDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Store existingStore = storeRepository.findById(id).orElse(null);
        if (existingStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingStore.setIsDeleted(true);
        storeRepository.save(toDto.map(existingStore, Store.class));
        return CompletableFuture.completedFuture(null);
    }


}

