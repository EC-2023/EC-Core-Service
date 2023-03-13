
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.Cart;

    import java.util.UUID;

@Repository
public interface ICartRepository extends JpaRepository<Cart, UUID> {
}

    