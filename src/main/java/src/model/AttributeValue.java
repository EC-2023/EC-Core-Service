package src.model;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "attribute_value")
@FilterDef(name = "isNotDeleted", parameters = @ParamDef(name = "isDeletedParam", type = boolean.class))
@Filter(name = "isNotDeleted", condition = "isDeleted = :isDeletedParam")
//@DynamicUpdate
//@DynamicInsert
@Data
public class AttributeValue {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "attribute_value_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = true)
    private String name;

    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;

    @Basic
    @Column(name = "createAt", nullable = false)
    private Date createAt = new Date(new java.util.Date().getTime());

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt = new Date(new java.util.Date().getTime());

    @Basic
    @Column(name = "attribute_id", nullable = true)
    private UUID attribute_id;

    @Basic
    @Column(name = "product_id", nullable = true)
    private UUID product_id;

    @Basic
    @Column(name = "order_item_id", nullable = true)
    private UUID orderItem_id;

    @Basic
    @Column(name = "cart_item_id", nullable = true)
    private UUID cartItem_id;


    @ManyToOne
    @JoinColumn(name = "cart_item_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
    private CartItems cartItemsByCartItemId;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id", nullable = true, insertable = false, updatable = false)
    @Where(clause = "is_deleted = false")
    private Attribute attributeByAttributeId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = true, insertable = false, updatable = false)
    private Product attributesValueByProductId;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
    private OrderItems attributesValueByOrderItemId;

    public AttributeValue() {
    }

    public AttributeValue(UUID attribute_id, UUID cartItemId, UUID product_id, UUID orderItem_id, String name) {
        this.attribute_id = attribute_id;
        this.cartItem_id = cartItemId;
        this.product_id = product_id;
        this.orderItem_id = orderItem_id;
        this.name = name;
    }


}
