
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.Store;

    import java.util.UUID;

@Repository
public interface IStoreRepository extends JpaRepository<Store, UUID> {
}

    