package src.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CartItemsRepository extends JpaRepository<CartItems, UUID> {
    @Transactional
    @Modifying
    @Query("delete from CartItems c")
    int deleteFirstBy();
}