

package src.service.Store;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
public class StoreService {
    @Autowired
    private IStoreRepository storeRepository;
    @Autowired
    private ModelMapper toDto;

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

