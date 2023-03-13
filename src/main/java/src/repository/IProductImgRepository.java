
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.ProductImg;

    import java.util.UUID;

@Repository
public interface IProductImgRepository extends JpaRepository<ProductImg, UUID> {
}

    