

package src.service.StoreLevel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.exception.NotFoundException;
import src.model.StoreLevel;
import src.repository.IStoreLevelRepository;
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
public class StoreLevelService {
    @Autowired
    private IStoreLevelRepository storelevelRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<StoreLevelDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<StoreLevelDto>) storelevelRepository.findAll().stream().map(
                        x -> toDto.map(x, StoreLevelDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<StoreLevelDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(storelevelRepository.findById(id).get(), StoreLevelDto.class));
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
        Date createAt = existingStoreLevel.getCreateAt();
        existingStoreLevel = toDto.map(storelevel, StoreLevel.class);
        existingStoreLevel.setId(id);
        existingStoreLevel.setCreateAt(createAt);
        return CompletableFuture.completedFuture(toDto.map(storelevelRepository.save(existingStoreLevel), StoreLevelDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        StoreLevel existingStoreLevel = storelevelRepository.findById(id).orElse(null);
        if (existingStoreLevel == null)
            throw new NotFoundException("Unable to find store level!");
        existingStoreLevel.setIsDeleted(true);
        storelevelRepository.save(existingStoreLevel);
        return CompletableFuture.completedFuture(null);
    }
}

