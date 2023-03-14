package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "delivery")
@Data
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
    public Delivery(UUID id, String name, double price, String description) {
        Id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public Delivery() {
    }
}
