
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import src.model.Orders;

    import java.util.List;
    import java.util.UUID;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT o FROM Orders o WHERE o.userId = ?1")
    List<Orders> getMyOrders(UUID id);
}

    