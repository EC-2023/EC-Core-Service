
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import src.model.StoreLevel;

    import java.util.UUID;

@Repository
public interface IStoreLevelRepository extends JpaRepository<StoreLevel, UUID> {
    @Query("SELECT p FROM StoreLevel p WHERE p.minPoint = (SELECT MIN(minPoint) FROM StoreLevel)")
    StoreLevel findMinStoreLevel();
}

    