
    package src.repository;

import src.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDeliveryRepository extends JpaRepository<Delivery, UUID> {
}

    