package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
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
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
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
    @OneToMany(mappedBy = "attributesValueByOrderItemId", fetch = FetchType.LAZY)
    private Collection<AttributeValue> attributesValueByOrderItemId;

}
