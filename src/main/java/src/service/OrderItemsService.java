
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.OrderItems;
import src.repository.IOrderItemsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderItemsService {
    private  IOrderItemsRepository orderitemsRepository;
    @Autowired
    public OrderItemsService(IOrderItemsRepository orderitemsRepository) {
        this.orderitemsRepository = orderitemsRepository;
    }

    public List<OrderItems> getAll() {
        return (List<OrderItems>) orderitemsRepository.findAll();
    }

    public Optional<OrderItems> getOne(UUID id) {
        return orderitemsRepository.findById(id);
    }

    public OrderItems create(OrderItems orderitems) {
        return orderitemsRepository.save(orderitems);
    }

    public OrderItems update(UUID id, OrderItems orderitems) {
        OrderItems existingOrderItems = orderitemsRepository.findById(id).orElse(null);
        if (existingOrderItems != null) {

            orderitemsRepository.save(existingOrderItems);
        }
        return existingOrderItems;
    }

    public void remove(UUID id) {
        orderitemsRepository.deleteById(id);
    }
}
