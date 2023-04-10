

package src.service.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.utils.ApiQuery;
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
    @PersistenceContext
    EntityManager em;

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
        existingRole.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(roleRepository.save(existingRole), RoleDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<RoleDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        ApiQuery<Role> features = new ApiQuery<>(request, em, Role.class);
        long total = roleRepository.count();
        return CompletableFuture.completedFuture(PagedResultDto.create(Pagination.create(total, skip, limit),
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, RoleDto.class)).toList()));
    }
    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find Role!");
        existingRole.setIsDeleted(true);
        existingRole.setUpdateAt(new Date(new java.util.Date().getTime()));
        roleRepository.save(toDto.map(existingRole, Role.class));
        return CompletableFuture.completedFuture(null);
    }


}

