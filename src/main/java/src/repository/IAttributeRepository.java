
    package src.repository;

import src.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAttributeRepository extends JpaRepository<Attribute, UUID> {
}

    