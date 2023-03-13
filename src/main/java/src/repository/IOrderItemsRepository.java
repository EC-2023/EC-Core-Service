
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.OrderItems;

    import java.util.UUID;

@Repository
public interface IOrderItemsRepository extends JpaRepository<OrderItems, UUID> {
}

    