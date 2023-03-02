
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Delivery;
import src.repository.IDeliveryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryService {
    private  IDeliveryRepository deliveryRepository;
    @Autowired
    public DeliveryService(IDeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> getAll() {
        return (List<Delivery>) deliveryRepository.findAll();
    }

    public Optional<Delivery> getOne(UUID id) {
        return deliveryRepository.findById(id);
    }

    public Delivery create(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public Delivery update(UUID id, Delivery delivery) {
        Delivery existingDelivery = deliveryRepository.findById(id).orElse(null);
        if (existingDelivery != null) {

            deliveryRepository.save(existingDelivery);
        }
        return existingDelivery;
    }

    public void remove(UUID id) {
        deliveryRepository.deleteById(id);
    }
}
