
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import src.model.Store;

    import java.util.Optional;
    import java.util.UUID;

@Repository
public interface IStoreRepository extends JpaRepository<Store, UUID> {
    @Query("SELECT s FROM Store s WHERE s.ownId = ?1 and s.isDeleted = false")
    Optional<Store> findByUserId(UUID id);
    @Override
    @Query("SELECT u FROM Store u WHERE u.Id = ?1")
    Optional<Store> findById(UUID id);

}

    