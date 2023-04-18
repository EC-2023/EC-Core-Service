
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.UserAddress;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserAddressRepository extends JpaRepository<UserAddress, UUID> {
    @Query("SELECT u FROM UserAddress u WHERE u.userId = ?1")
    public List<UserAddress> findByUserId(UUID id);
}

    