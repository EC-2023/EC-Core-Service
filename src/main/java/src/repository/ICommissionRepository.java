
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.model.Commission;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ICommissionRepository extends JpaRepository<Commission, UUID> {
    Collection<Object> findByNameContainingIgnoreCase(String name);
}

    