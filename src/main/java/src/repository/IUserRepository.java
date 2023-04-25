
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    @Override
    @Query("SELECT u FROM User u WHERE u.Id = ?1")
    Optional<User> findById(UUID id);
    @Query("SELECT u FROM User u WHERE u.phoneNumber = ?1")
    Optional<User> findByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.tokenResetPassword = ?1")
    Optional<User> findByTokenResetPassword(String token);
}

    