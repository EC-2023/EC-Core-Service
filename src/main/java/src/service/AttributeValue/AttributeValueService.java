

package src.service.AttributeValue;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.AttributeValue;
import src.repository.IAttributeValueRepository;
import src.service.AttributeValue.Dtos.AttributeValueCreateDto;
import src.service.AttributeValue.Dtos.AttributeValueDto;
import src.service.AttributeValue.Dtos.AttributeValueUpdateDto;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AttributeValueService {
    @Autowired
    private IAttributeValueRepository attributevalueRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<AttributeValueDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<AttributeValueDto>) attributevalueRepository.findAll().stream().map(
                        x -> toDto.map(x, AttributeValueDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<AttributeValueDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(attributevalueRepository.findById(id), AttributeValueDto.class));
    }

    @Async
    public CompletableFuture<AttributeValueDto> create(AttributeValueCreateDto input) {
        AttributeValue attributevalue = attributevalueRepository.save(toDto.map(input, AttributeValue.class));
        return CompletableFuture.completedFuture(toDto.map(attributevalueRepository.save(attributevalue), AttributeValueDto.class));
    }

    @Async
    public CompletableFuture<AttributeValueDto> update(UUID id, AttributeValueUpdateDto attributevalue) {
        AttributeValue existingAttributeValue = attributevalueRepository.findById(id).orElse(null);
        if (existingAttributeValue == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(attributevalueRepository.save(toDto.map(attributevalue, AttributeValue.class)), AttributeValueDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        AttributeValue existingAttributeValue = attributevalueRepository.findById(id).orElse(null);
        if (existingAttributeValue == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingAttributeValue.setIsDeleted(true);
        attributevalueRepository.save(toDto.map(existingAttributeValue, AttributeValue.class));
        return CompletableFuture.completedFuture(null);
    }

    // tìm kiếm Attribute Value theo name
    @Async
    public CompletableFuture<List<AttributeValueDto>> findByName(String name) {
        Collection<Object> attributevalues = attributevalueRepository.findByNameContainingIgnoreCase(name);
        List<AttributeValueDto> attributevalueDtos = new ArrayList<>();

        for (Object attributevalue : attributevalues) {
            AttributeValueDto attributevalueDto = toDto.map(attributevalue, AttributeValueDto.class);
            if (attributevalueDto.getName().equalsIgnoreCase(name)) {
                attributevalueDtos.add(attributevalueDto);
            }
        }
        if (attributevalueDtos.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find any attributes with name: " + name);
        }
        return CompletableFuture.completedFuture(attributevalueDtos);
    }

    // sắp xếp Attribute Value theo name
    @Async
    public CompletableFuture<List<AttributeValueDto>> getAllSortedByName() {
        List<AttributeValueDto> attributevalueDtos = attributevalueRepository.findAll().stream()
                .map(attributevalue -> toDto.map(attributevalue, AttributeValueDto.class))
                .sorted(Comparator.comparing(AttributeValueDto::getName))
                .collect(Collectors.toList());
        return CompletableFuture.completedFuture(attributevalueDtos);
    }
}

