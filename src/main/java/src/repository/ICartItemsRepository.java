
    package src.repository;

import org.springframework.data.jpa.repository.Query;
import src.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICartItemsRepository extends JpaRepository<CartItems, UUID> {

    @Query("SELECT i FROM CartItems i WHERE i.cartId = ?1 and i.productId = ?2")
    CartItems findCartItemsByCartIdAndProductId(UUID cartId, UUID productId);

}

    