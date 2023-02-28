
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Orders;
import src.repository.IOrdersRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdersService {
    private  IOrdersRepository ordersRepository;
    @Autowired
    public OrdersService(IOrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Orders> getAll() {
        return (List<Orders>) ordersRepository.findAll();
    }

    public Optional<Orders> getOne(UUID id) {
        return ordersRepository.findById(id);
    }

    public Orders create(Orders orders) {
        return ordersRepository.save(orders);
    }

    public Orders update(UUID id, Orders orders) {
        Orders existingOrders = ordersRepository.findById(id).orElse(null);
        if (existingOrders != null) {

            ordersRepository.save(existingOrders);
        }
        return existingOrders;
    }

    public void remove(UUID id) {
        ordersRepository.deleteById(id);
    }
}
