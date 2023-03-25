
    package src.repository;

import src.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICartItemsRepository extends JpaRepository<CartItems, UUID> {
}

    