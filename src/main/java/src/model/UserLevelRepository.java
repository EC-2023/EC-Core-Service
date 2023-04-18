package src.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserLevelRepository extends JpaRepository<UserLevel, UUID> {
}