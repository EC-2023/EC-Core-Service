

package src.service.Attribute;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.Attribute;
import src.repository.IAttributeRepository;
import src.service.Attribute.Dtos.AttributeCreateDto;
import src.service.Attribute.Dtos.AttributeDto;
import src.service.Attribute.Dtos.AttributeUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AttributeService {
    @Autowired
    private IAttributeRepository attributeRepository;
    @Autowired
    private ModelMapper toDto;

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
    public CompletableFuture<AttributeDto> update(UUID id, AttributeUpdateDto attribute) {
        Attribute existingAttribute = attributeRepository.findById(id).orElse(null);
        if (existingAttribute == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(attributeRepository.save(toDto.map(attribute, Attribute.class)), AttributeDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Attribute existingAttribute = attributeRepository.findById(id).orElse(null);
        if (existingAttribute == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingAttribute.setDeleted(true);
        attributeRepository.save(toDto.map(existingAttribute, Attribute.class));
        return CompletableFuture.completedFuture(null);
    }
}

