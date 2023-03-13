
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.Product;

    import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
}

    