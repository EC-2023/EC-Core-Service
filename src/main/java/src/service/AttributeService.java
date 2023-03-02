
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Attribute;
import src.repository.IAttributeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttributeService {
    private  IAttributeRepository attributeRepository;
    @Autowired
    public AttributeService(IAttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public List<Attribute> getAll() {
        return (List<Attribute>) attributeRepository.findAll();
    }

    public Optional<Attribute> getOne(UUID id) {
        return attributeRepository.findById(id);
    }

    public Attribute create(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    public Attribute update(UUID id, Attribute attribute) {
        Attribute existingAttribute = attributeRepository.findById(id).orElse(null);
        if (existingAttribute != null) {

            attributeRepository.save(existingAttribute);
        }
        return existingAttribute;
    }

    public void remove(UUID id) {
        attributeRepository.deleteById(id);
    }
}
