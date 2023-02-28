
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.CartItems;
import src.repository.ICartItemsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemsService {
    private  ICartItemsRepository cartitemsRepository;
    @Autowired
    public CartItemsService(ICartItemsRepository cartitemsRepository) {
        this.cartitemsRepository = cartitemsRepository;
    }

    public List<CartItems> getAll() {
        return (List<CartItems>) cartitemsRepository.findAll();
    }

    public Optional<CartItems> getOne(UUID id) {
        return cartitemsRepository.findById(id);
    }

    public CartItems create(CartItems cartitems) {
        return cartitemsRepository.save(cartitems);
    }

    public CartItems update(UUID id, CartItems cartitems) {
        CartItems existingCartItems = cartitemsRepository.findById(id).orElse(null);
        if (existingCartItems != null) {

            cartitemsRepository.save(existingCartItems);
        }
        return existingCartItems;
    }

    public void remove(UUID id) {
        cartitemsRepository.deleteById(id);
    }
}
