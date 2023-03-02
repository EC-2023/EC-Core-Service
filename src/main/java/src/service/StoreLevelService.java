
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.StoreLevel;
import src.repository.IStoreLevelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreLevelService {
    private  IStoreLevelRepository storelevelRepository;
    @Autowired
    public StoreLevelService(IStoreLevelRepository storelevelRepository) {
        this.storelevelRepository = storelevelRepository;
    }

    public List<StoreLevel> getAll() {
        return (List<StoreLevel>) storelevelRepository.findAll();
    }

    public Optional<StoreLevel> getOne(UUID id) {
        return storelevelRepository.findById(id);
    }

    public StoreLevel create(StoreLevel storelevel) {
        return storelevelRepository.save(storelevel);
    }

    public StoreLevel update(UUID id, StoreLevel storelevel) {
        StoreLevel existingStoreLevel = storelevelRepository.findById(id).orElse(null);
        if (existingStoreLevel != null) {

            storelevelRepository.save(existingStoreLevel);
        }
        return existingStoreLevel;
    }

    public void remove(UUID id) {
        storelevelRepository.deleteById(id);
    }
}
