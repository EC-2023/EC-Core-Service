
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.Attribute;

    import java.util.UUID;

@Repository
public interface IAttributeRepository extends JpaRepository<Attribute, UUID> {
}

    