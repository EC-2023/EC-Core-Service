

package src.service.Role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
import src.model.Role;
import src.repository.IRoleRepository;
import src.service.Role.Dtos.RoleDto;
import src.service.Role.Dtos.RoleCreateDto;
import src.service.Role.Dtos.RoleUpdateDto;

import java.util.*;
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
    public CompletableFuture<RoleDto> update(UUID id, RoleUpdateDto roles) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole == null)
            throw new NotFoundException("Unable to find role!");
        BeanUtils.copyProperties(roles, existingRole);
        return CompletableFuture.completedFuture(toDto.map(roleRepository.save(existingRole), RoleDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingRole.setIsDeleted(true);
        roleRepository.save(toDto.map(existingRole, Role.class));
        return CompletableFuture.completedFuture(null);
    }

    // tìm kiếm Role theo name
    @Async
    public CompletableFuture<List<RoleDto>> findByName(String name) {
        Collection<Object> roles = roleRepository.findByNameContainingIgnoreCase(name);
        List<RoleDto> roleDtos = new ArrayList<>();

        for (Object role : roles) {
            RoleDto aoleDto = toDto.map(role, RoleDto.class);
            if (aoleDto.getName().equalsIgnoreCase(name)) {
                roleDtos.add(aoleDto);
            }
        }
        if (roleDtos.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find any roles with name: " + name);
        }
        return CompletableFuture.completedFuture(roleDtos);
    }


    // sắp xếp Role theo name
    @Async
    public CompletableFuture<List<RoleDto>> getAllSortedByName() {
        List<RoleDto> roleDtos = roleRepository.findAll().stream()
                .map(role -> toDto.map(role, RoleDto.class))
                .sorted(Comparator.comparing(RoleDto::getName))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(roleDtos);
    }
}

