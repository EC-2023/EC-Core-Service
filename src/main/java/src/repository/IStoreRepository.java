
    package src.repository;

import src.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IStoreRepository extends JpaRepository<Store, UUID> {
}

    