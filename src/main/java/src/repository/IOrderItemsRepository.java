
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import src.model.OrderItems;

    import java.util.List;
    import java.util.UUID;

@Repository
public interface IOrderItemsRepository extends JpaRepository<OrderItems, UUID> {
    @Query("SELECT i FROM OrderItems i WHERE i.orderId = ?1")
    List<OrderItems> findByOrderId(UUID id);
}

    