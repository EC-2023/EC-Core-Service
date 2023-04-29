
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import src.model.AttributeValue;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface IAttributeValueRepository extends JpaRepository<AttributeValue, UUID> {
    Collection<Object> findByNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM AttributeValue a WHERE a.cartItem_id = ?1")
    List<AttributeValue> findByCartItem_id(UUID id);

    @Query("SELECT a FROM AttributeValue a WHERE a.attribute_id = ?1")
    List<AttributeValue> findAllByAttributeId(UUID id);
    @Transactional
    @Modifying
    @Query("DELETE FROM AttributeValue ci WHERE ci.cartItem_id = ?1")
    void  deleteByCartItemId(UUID id);
}

    