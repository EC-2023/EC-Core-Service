
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.model.UserAddress;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface IUserAddressRepository extends JpaRepository<UserAddress, UUID> {
    Collection<Object> findByCountryContainingIgnoreCase(String country);

    Collection<Object> findByCityContainingIgnoreCase(String city);
}

    