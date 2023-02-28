
    package src.repository;

import src.model.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAttributeValueRepository extends JpaRepository<AttributeValue, UUID> {
}

    