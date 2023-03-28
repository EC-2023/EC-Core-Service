
    package src.repository;

import src.model.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductImgRepository extends JpaRepository<ProductImg, UUID> {
}

    