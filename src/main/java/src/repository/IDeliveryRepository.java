
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.Delivery;

    import java.util.UUID;

@Repository
public interface IDeliveryRepository extends JpaRepository<Delivery, UUID> {
}

    