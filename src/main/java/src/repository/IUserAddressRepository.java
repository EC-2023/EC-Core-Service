
    package src.repository;

import src.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserAddressRepository extends JpaRepository<UserAddress, UUID> {
}

    