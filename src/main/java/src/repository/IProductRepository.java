
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
    import src.model.Product;

    import java.util.List;
    import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByNameContainingIgnoreCase(String keyword);
    @Query("SELECT sum(1) FROM Product s WHERE s.storeId = ?1 and s.isDeleted = false")
    Integer getCountProductByStore(UUID id);
}

    