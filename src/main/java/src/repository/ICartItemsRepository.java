
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.model.CartItems;

import java.util.UUID;

@Repository
public interface ICartItemsRepository extends JpaRepository<CartItems, UUID> {
}

    