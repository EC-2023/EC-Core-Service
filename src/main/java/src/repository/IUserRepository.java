
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.User;

    import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
}

    