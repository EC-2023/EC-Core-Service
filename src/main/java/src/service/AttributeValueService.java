
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.AttributeValue;
import src.repository.IAttributeValueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttributeValueService {
    private  IAttributeValueRepository attributevalueRepository;
    @Autowired
    public AttributeValueService(IAttributeValueRepository attributevalueRepository) {
        this.attributevalueRepository = attributevalueRepository;
    }

    public List<AttributeValue> getAll() {
        return (List<AttributeValue>) attributevalueRepository.findAll();
    }

    public Optional<AttributeValue> getOne(UUID id) {
        return attributevalueRepository.findById(id);
    }

    public AttributeValue create(AttributeValue attributevalue) {
        return attributevalueRepository.save(attributevalue);
    }

    public AttributeValue update(UUID id, AttributeValue attributevalue) {
        AttributeValue existingAttributeValue = attributevalueRepository.findById(id).orElse(null);
        if (existingAttributeValue != null) {

            attributevalueRepository.save(existingAttributeValue);
        }
        return existingAttributeValue;
    }

    public void remove(UUID id) {
        attributevalueRepository.deleteById(id);
    }
}
