package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "attribute_value")
public class AttributeValue {

    public UUID getAttributeValueId() {
        return Id;
    }

    public void setAttributeValueId(UUID Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateDate() {
        return createAt;
    }

    public void setCreateDate(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateDate() {
        return updateAt;
    }

    public void setUpdateDate(Date updateAt) {
        this.updateAt = updateAt;
    }

    public UUID getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(UUID attribute_id) {
        this.attribute_id = attribute_id;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public UUID getOrderItem_id() {
        return orderItem_id;
    }

    public void setOrderItem_id(UUID orderItem_id) {
        this.orderItem_id = orderItem_id;
    }

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "attribute_value_id", nullable = false)
    private UUID Id;

    @Basic
    @Column(name = "name", nullable = true)
    private String name;

    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted;

    @Basic
    @Column(name = "createAt", nullable = false)
    private Date createAt= new Date(new java.util.Date().getTime());

    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());

    @Basic
    @Column(name = "attribute_id", nullable = true)
    private UUID attribute_id;

    @Basic
    @Column(name = "product_id", nullable = true)
    private UUID product_id;

    @Basic
    @Column(name = "order_item_id", nullable = true)
    private UUID orderItem_id;

    public Attribute getAttributeByAttributeId() {
        return attributeByAttributeId;
    }

    public void setAttributeByAttributeId(Attribute attributeByAttributeId) {
        this.attributeByAttributeId = attributeByAttributeId;
    }

    public Product getAttributesValueByProductId() {
        return attributesValueByProductId;
    }

    public void setAttributesValueByProductId(Product attributesValueByProductId) {
        this.attributesValueByProductId = attributesValueByProductId;
    }

    public OrderItems getAttributesValueByOrderItemId() {
        return attributesValueByOrderItemId;
    }

    public void setAttributesValueByOrderItemId(OrderItems attributesValueByOrderItemId) {
        this.attributesValueByOrderItemId = attributesValueByOrderItemId;
    }

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id", nullable = true, insertable = false, updatable = false)
    private Attribute attributeByAttributeId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = true, insertable = false, updatable = false)
    private Product attributesValueByProductId;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
    private OrderItems attributesValueByOrderItemId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeValue cartItems = (AttributeValue) o;
        return Id == cartItems.Id && name == cartItems.name && product_id == cartItems.product_id
                && attribute_id == cartItems.attribute_id && orderItem_id == cartItems.orderItem_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute_id, Id, name, product_id, orderItem_id);
    }
}
