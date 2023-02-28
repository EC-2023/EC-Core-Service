
    package src.repository;

import src.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICommissionRepository extends JpaRepository<Commission, UUID> {
}

    