package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "order_items",  catalog = "")
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

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Collection<AttributeValue> getAttributesValueByOrderItemId() {
        return attributesValueByOrderItemId;
    }

    public void setAttributesValueByOrderItemId(Collection<AttributeValue> attributesValueByOrderItemId) {
        this.attributesValueByOrderItemId = attributesValueByOrderItemId;
    }

    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted= false;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    private Product productByProductId;

    @OneToMany(mappedBy = "attributesValueByOrderItemId", fetch = FetchType.LAZY)
    private Collection<AttributeValue> attributesValueByOrderItemId;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        this.Id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItems that = (OrderItems) o;
        return Id == that.Id && productId == that.productId && quantity == that.quantity;
    }

    public Product getProductByProductId() {
        return productByProductId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, productId,quantity,order, isDeleted, createAt, updateAt);
    }
    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }
}
