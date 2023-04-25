

package src.service.Store;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.Orders;
import src.model.Store;
import src.repository.ICommissionRepository;
import src.repository.IOrdersRepository;
import src.repository.IStoreLevelRepository;
import src.repository.IStoreRepository;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Store.Dtos.StoreCreateDto;
import src.service.Store.Dtos.StoreDto;
import src.service.Store.Dtos.StoreUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class StoreService implements IStoreService {
    private final IStoreRepository storeRepository;
    private final IOrdersRepository ordersRepository;
    private final IStoreLevelRepository storeLevelRepository;
    private final ICommissionRepository commissionRepository;
    private final ModelMapper toDto;
    @PersistenceContext
    EntityManager em;

    public StoreService(IStoreRepository storeRepository, IOrdersRepository ordersRepository, IStoreLevelRepository storeLevelRepository, ICommissionRepository commissionRepository, ModelMapper toDto) {
        this.storeRepository = storeRepository;
        this.ordersRepository = ordersRepository;
        this.storeLevelRepository = storeLevelRepository;
        this.commissionRepository = commissionRepository;
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

    public CompletableFuture<StoreDto> create(StoreCreateDto input) {
        return null;
    }

    @Async
    public CompletableFuture<StoreDto> create(StoreCreateDto input, UUID userId) {
        Store store = toDto.map(input, Store.class);
        store.setStoreLevelId(storeLevelRepository.findMinStoreLevel().getId());
        store.setCommissionId(commissionRepository.findMinCommission().getId());
        store.setOwnId(userId);
        return CompletableFuture.completedFuture(toDto.map(storeRepository.save(store), StoreDto.class));
    }

    @Async
    public CompletableFuture<StoreDto> update(UUID id, StoreUpdateDto store) {
        Store existingStore = storeRepository.findById(id).orElse(null);
        if (existingStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find store!");
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
    @Override
    public CompletableFuture<Void> remove(UUID id) {
        Store existingStore = storeRepository.findById(id).orElse(null);
        if (existingStore == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find store!");
        existingStore.setIsDeleted(true);
        storeRepository.save(toDto.map(existingStore, Store.class));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<PagedResultDto<OrdersDto>> findOrderByStore(HttpServletRequest request, Integer limit, Integer skip) {
        UUID userId = ((UUID) (request.getAttribute("id")));
        Store existingStore = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Unable to find store!"));
        request.setAttribute("storeId%7B%7Beq%7D%7D", existingStore.getId());
        Pagination pagination = Pagination.create(0, skip, limit);
        ApiQuery<Orders> features = new ApiQuery<>(request, em, Orders.class, pagination);
        long total = features.filter().orderBy().exec().size();
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x ->
                        {
                            Hibernate.initialize(x.getDeliveryByDeliveryId());
                            Hibernate.initialize(x.getItem());
                            return toDto.map(x, OrdersDto.class);
                        }

                ).toList()));
    }


    @Async
    @Override
    public CompletableFuture<StoreDto> setActiveStore(UUID storeId, boolean status) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new NotFoundException("Unable to find store!"));
        store.setActive(status);
        store.setUpdateAt(new Date(new java.util.Date().getTime()));
        store = storeRepository.save(store);
        return CompletableFuture.completedFuture(toDto.map(store, StoreDto.class));
    }
}

