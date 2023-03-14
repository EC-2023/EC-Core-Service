
    package src.repository;

import src.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderItemsRepository extends JpaRepository<OrderItems, UUID> {
}

    