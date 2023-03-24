

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

    // tìm kiếm Commission theo name
    @Async
    public CompletableFuture<List<CommissionDto>> findByName(String name) {
        Collection<Object> commissions = commissionRepository.findByNameContainingIgnoreCase(name);
        List<CommissionDto> commissionDtos = new ArrayList<>();

        for (Object commission : commissions) {
            CommissionDto commissionDto = toDto.map(commission, CommissionDto.class);
            if (commissionDto.getName().equalsIgnoreCase(name)) {
                commissionDtos.add(commissionDto);
            }
        }
        if (commissionDtos.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find any commissions with name: " + name);
        }
        return CompletableFuture.completedFuture(commissionDtos);
    }

    // sắp xếp Commission theo name
    @Async
    public CompletableFuture<List<CommissionDto>> getAllSortedByName() {
        List<CommissionDto> commissionDtos = commissionRepository.findAll().stream()
                .map(commission -> toDto.map(commission, CommissionDto.class))
                .sorted(Comparator.comparing(CommissionDto::getName))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(commissionDtos);
    }
}

