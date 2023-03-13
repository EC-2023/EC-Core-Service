
    package src.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import src.model.UserFollowStore;

    import java.util.UUID;

@Repository
public interface IUserFollowStoreRepository extends JpaRepository<UserFollowStore, UUID> {
}

    