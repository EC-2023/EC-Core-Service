

package src.service.Attribute;

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
import src.model.Attribute;
import src.repository.IAttributeRepository;
import src.service.Attribute.Dtos.AttributeCreateDto;
import src.service.Attribute.Dtos.AttributeDto;
import src.service.Attribute.Dtos.AttributeUpdateDto;
import src.service.UserLevel.Dtos.UserLevelDto;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AttributeService {
    @Autowired
    private IAttributeRepository attributeRepository;
    @Autowired
    private ModelMapper toDto;
    @PersistenceContext
    EntityManager em;
    @Async
    public CompletableFuture<List<AttributeDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<AttributeDto>) attributeRepository.findAll().stream().map(
                        x -> toDto.map(x, AttributeDto.class)
                ).collect(Collectors.toList()));
    }
    @Async
    public CompletableFuture<AttributeDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(attributeRepository.findById(id), AttributeDto.class));
    }

    @Async
    public CompletableFuture<AttributeDto> create(AttributeCreateDto input) {
        Attribute attribute = attributeRepository.save(toDto.map(input, Attribute.class));
        return CompletableFuture.completedFuture(toDto.map(attributeRepository.save(attribute), AttributeDto.class));
    }

    @Async
    public CompletableFuture<AttributeDto> update(UUID id, AttributeUpdateDto attributes) {
        Attribute existingAttribute = attributeRepository.findById(id).orElse(null);
        if (existingAttribute == null)
            throw new NotFoundException("Unable to find Attribute!");
        BeanUtils.copyProperties(attributes, existingAttribute);
        existingAttribute.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(attributeRepository.save(existingAttribute), AttributeDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<AttributeDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        ApiQuery<Attribute> features = new ApiQuery<>(request, em, Attribute.class);
        long total = attributeRepository.count();
        return CompletableFuture.completedFuture(PagedResultDto.create(Pagination.create(total, skip, limit),
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, AttributeDto.class)).toList()));
    }
    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Attribute existingAttribute = attributeRepository.findById(id).orElse(null);
        if (existingAttribute == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find Attribute!");
        existingAttribute.setIsDeleted(true);
        existingAttribute.setUpdateAt(new Date(new java.util.Date().getTime()));
        attributeRepository.save(toDto.map(existingAttribute, Attribute.class));
        return CompletableFuture.completedFuture(null);
    }


}

