package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "delivery")
public class Delivery {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createDate", nullable = false)
    private Date createDate = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateDate", nullable = true)
    private Date updateDate;
    @OneToMany(mappedBy = "deliveryByDeliveryId")
    private Collection<Orders> ordersByDeliveryId;

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return deliveryId == delivery.deliveryId && Double.compare(delivery.price, price) == 0 && Objects.equals(name, delivery.name) && Objects.equals(description, delivery.description) && Objects.equals(isDeleted, delivery.isDeleted) && Objects.equals(createDate, delivery.createDate) && Objects.equals(updateDate, delivery.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, name, price, description, isDeleted, createDate, updateDate);
    }

    public Collection<Orders> getOrdersByDeliveryId() {
        return ordersByDeliveryId;
    }

    public void setOrdersByDeliveryId(Collection<Orders> ordersByDeliveryId) {
        this.ordersByDeliveryId = ordersByDeliveryId;
    }
}
