
    package src.repository;

import src.model.UserFollowStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserFollowStoreRepository extends JpaRepository<UserFollowStore, UUID> {
}

    