
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Store;
import src.repository.IStoreRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreService {
    private  IStoreRepository storeRepository;
    @Autowired
    public StoreService(IStoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAll() {
        return (List<Store>) storeRepository.findAll();
    }

    public Optional<Store> getOne(UUID id) {
        return storeRepository.findById(id);
    }

    public Store create(Store store) {
        return storeRepository.save(store);
    }

    public Store update(UUID id, Store store) {
        Store existingStore = storeRepository.findById(id).orElse(null);
        if (existingStore != null) {

            storeRepository.save(existingStore);
        }
        return existingStore;
    }

    public void remove(UUID id) {
        storeRepository.deleteById(id);
    }
}
