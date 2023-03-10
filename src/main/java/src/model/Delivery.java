package src.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "delivery")
public class Delivery {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "delivery_id", nullable = false)
    private UUID Id;
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
    @Column(name = "createAt", nullable = false)
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "deliveryByDeliveryId")
    private Collection<Orders> ordersByDeliveryId;

    public UUID getDeliveryId() {
        return Id;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.Id = deliveryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Id == delivery.Id && Double.compare(delivery.price, price) == 0 && Objects.equals(name, delivery.name) && Objects.equals(description, delivery.description) && Objects.equals(isDeleted, delivery.isDeleted) && Objects.equals(createAt, delivery.createAt) && Objects.equals(updateAt, delivery.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, price, description, isDeleted, createAt, updateAt);
    }

    public Collection<Orders> getOrdersByDeliveryId() {
        return ordersByDeliveryId;
    }

    public void setOrdersByDeliveryId(Collection<Orders> ordersByDeliveryId) {
        this.ordersByDeliveryId = ordersByDeliveryId;
    }
}
