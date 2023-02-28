
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.UserLevel;

    import java.util.UUID;

@Repository
public interface IUserLevelRepository extends JpaRepository<UserLevel, UUID> {

}

    