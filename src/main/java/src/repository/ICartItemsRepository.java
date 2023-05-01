
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.CartItems;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICartItemsRepository extends JpaRepository<CartItems, UUID> {

    @Query("SELECT i FROM CartItems i WHERE i.cartId = ?1 and i.productId = ?2")
    CartItems findCartItemsByCartIdAndProductId(UUID cartId, UUID productId);

    @Query("SELECT i FROM CartItems i WHERE i.productId = ?1")
    List<CartItems> findByCartItemByProductId(UUID id);

    @Query("select  i from CartItems i where i.Id = ?1")
    Optional<CartItems> findById(UUID id);

}

    