
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.Cart;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICartRepository extends JpaRepository<Cart, UUID> {

    @Query("SELECT c FROM Cart c WHERE c.userId = ?1")
    List<Cart> findCartsByUserId(UUID id);

    @Query("SELECT c FROM Cart c WHERE c.userId = ?1 and c.storeId = ?2")
    Cart findCartsByUserIdAndStoreId(UUID userId, UUID storeId);

}

    