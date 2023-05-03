
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.Commission;

import java.util.UUID;

@Repository
public interface ICommissionRepository extends JpaRepository<Commission, UUID> {
    @Query("SELECT p FROM Commission p WHERE p.cost = (SELECT MIN(cost) FROM Commission)")
    Commission findMinCommission();
}

    