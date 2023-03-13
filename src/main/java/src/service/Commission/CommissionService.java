

package src.service.Commission;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.Commission;
import src.repository.ICommissionRepository;
import src.service.Commission.Dtos.CommissionCreateDto;
import src.service.Commission.Dtos.CommissionDto;
import src.service.Commission.Dtos.CommissionUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CommissionService {
    @Autowired
    private ICommissionRepository commissionRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<CommissionDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<CommissionDto>) commissionRepository.findAll().stream().map(
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
    public CompletableFuture<CommissionDto> update(UUID id, CommissionUpdateDto commission) {
        Commission existingCommission = commissionRepository.findById(id).orElse(null);
        if (existingCommission == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(commissionRepository.save(toDto.map(commission, Commission.class)), CommissionDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Commission existingCommission = commissionRepository.findById(id).orElse(null);
        if (existingCommission == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingCommission.setDeleted(true);
        commissionRepository.save(toDto.map(existingCommission, Commission.class));
        return CompletableFuture.completedFuture(null);
    }
}

