
    package src.repository;

import org.springframework.data.jpa.repository.Query;
import src.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static javax.swing.text.html.HTML.Tag.SELECT;

    @Repository
public interface ICartRepository extends JpaRepository<Cart, UUID> {

    @Query("SELECT c FROM Cart c WHERE c.userId = ?1")
    List<Cart> findCartsByUserId(UUID id);

}

    