

package src.service.Role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.Role;
import src.repository.IRoleRepository;
import src.service.Role.Dtos.RoleCreateDto;
import src.service.Role.Dtos.RoleDto;
import src.service.Role.Dtos.RoleUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<RoleDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<RoleDto>) roleRepository.findAll().stream().map(
                        x -> toDto.map(x, RoleDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<RoleDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(roleRepository.findById(id), RoleDto.class));
    }

    @Async
    public CompletableFuture<RoleDto> create(RoleCreateDto input) {
        Role role = roleRepository.save(toDto.map(input, Role.class));
        return CompletableFuture.completedFuture(toDto.map(role, RoleDto.class));
    }

    @Async
    public CompletableFuture<RoleDto> update(UUID id, RoleUpdateDto role) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(roleRepository.save(toDto.map(role, Role.class)), RoleDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingRole.setDeleted(true);
        roleRepository.save(toDto.map(existingRole, Role.class));
        return CompletableFuture.completedFuture(null);
    }
}

