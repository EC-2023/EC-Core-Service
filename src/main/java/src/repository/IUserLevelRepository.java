
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.UserLevel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserLevelRepository extends JpaRepository<UserLevel, UUID> {
    @Query("SELECT y FROM UserLevel y WHERE y.minPoint = (SELECT MIN(y2.minPoint) FROM UserLevel y2)")
    Optional<UserLevel> findByMinPoint();
}

    