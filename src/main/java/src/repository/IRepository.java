package src.repository;

import org.springframework.data.repository.CrudRepository;
import src.model.User;

import java.util.UUID;

public interface IRepository extends CrudRepository<User, UUID> {
}
