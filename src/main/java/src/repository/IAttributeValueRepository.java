
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.model.AttributeValue;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface IAttributeValueRepository extends JpaRepository<AttributeValue, UUID> {
    Collection<Object> findByNameContainingIgnoreCase(String name);
}

    