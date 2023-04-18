package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_items",  catalog = "")
@Data
public class OrderItems {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "product_id", nullable = false)
    private UUID productId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
    @Basic
    @Column(name = "createAt", nullable = false)
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    public Boolean getDeleted() {
        return isDeleted;
    }
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted= false;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    private Product productByProductId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable = false, updatable = false)
    private Orders orderByOrderId;
    @OneToMany(mappedBy = "attributesValueByOrderItemId", fetch = FetchType.LAZY)
    private Collection<AttributeValue> attributesValueByOrderItemId;

    public OrderItems() {
    }
    public OrderItems(UUID productId, int quantity, UUID orderId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }

}
