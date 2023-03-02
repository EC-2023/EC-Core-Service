
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.Cart;
import src.repository.ICartRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    private  ICartRepository cartRepository;
    @Autowired
    public CartService(ICartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAll() {
        return (List<Cart>) cartRepository.findAll();
    }

    public Optional<Cart> getOne(UUID id) {
        return cartRepository.findById(id);
    }

    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart update(UUID id, Cart cart) {
        Cart existingCart = cartRepository.findById(id).orElse(null);
        if (existingCart != null) {

            cartRepository.save(existingCart);
        }
        return existingCart;
    }

    public void remove(UUID id) {
        cartRepository.deleteById(id);
    }
}
