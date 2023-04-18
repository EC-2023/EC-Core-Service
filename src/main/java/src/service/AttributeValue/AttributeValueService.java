

package src.service.AttributeValue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.AttributeValue;
import src.repository.IAttributeValueRepository;
import src.service.AttributeValue.Dtos.AttributeValueCreateDto;
import src.service.AttributeValue.Dtos.AttributeValueDto;
import src.service.AttributeValue.Dtos.AttributeValueUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AttributeValueService {
    @Autowired
    private IAttributeValueRepository attributevalueRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<AttributeValueDto>> getAll() {
        return CompletableFuture.completedFuture(
                attributevalueRepository.findAll().stream().map(
                        x -> toDto.map(x, AttributeValueDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<AttributeValueDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(attributevalueRepository.findById(id), AttributeValueDto.class));
    }

    @Async
    public CompletableFuture<AttributeValueDto> create(AttributeValueCreateDto input) {
        AttributeValue attributevalue = toDto.map(input, AttributeValue.class);
        return CompletableFuture.completedFuture(toDto.map(attributevalueRepository.save(attributevalue), AttributeValueDto.class));
    }

    @Async
    public CompletableFuture<AttributeValueDto> update(UUID id, AttributeValueUpdateDto attributevalues) {
        AttributeValue existingAttributeValue = attributevalueRepository.findById(id).orElse(null);
        if (existingAttributeValue == null)
            throw new NotFoundException("Unable to find Attribute Value!");
        BeanUtils.copyProperties(attributevalues, existingAttributeValue);
        existingAttributeValue.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(attributevalueRepository.save(existingAttributeValue), AttributeValueDto.class));
    }
    @Async
    public CompletableFuture<PagedResultDto<AttributeValueDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = attributevalueRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<AttributeValue> features = new ApiQuery<>(request, em, AttributeValue.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, AttributeValueDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        AttributeValue existingAttributeValue = attributevalueRepository.findById(id).orElse(null);
        if (existingAttributeValue == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find Attribute Value!");
        existingAttributeValue.setIsDeleted(true);
        existingAttributeValue.setUpdateAt(new Date(new java.util.Date().getTime()));
        attributevalueRepository.save(toDto.map(existingAttributeValue, AttributeValue.class));
        return CompletableFuture.completedFuture(null);
    }


}

