
    package src.repository;

import src.model.StoreLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IStoreLevelRepository extends JpaRepository<StoreLevel, UUID> {
}

    