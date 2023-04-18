package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Orders {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "order_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Basic
    @Column(name = "store_id", nullable = false)
    private UUID storeId;
    @Basic
    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;
    @Basic
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;
    @Basic
    @Column(name = "status", nullable = false)
    private int status;
    @Basic
    @Column(name = "isPaidBefore", nullable = false)
    private boolean isPaidBefore;
    @Basic
    @Column(name = "amountFromUser", nullable = false, precision = 0)
    private double amountFromUser;
    @Basic
    @Column(name = "amountToStore", nullable = false, precision = 0)
    private double amountToStore;
    @Basic
    @Column(name = "amountToGD", nullable = false, precision = 0)
    private double amountToGd;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted= false;
    @Basic
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByUserId;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false, insertable = false, updatable = false)
    private Store storeByStoreId;
    @ManyToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id", nullable = false, insertable = false, updatable = false)
    private Delivery deliveryByDeliveryId;
    @OneToMany(mappedBy = "orderByOrderId", fetch = FetchType.EAGER)
    private Collection<OrderItems> item;
    public Orders(UUID userId,UUID storeId, UUID deliveryId, String address, String phone, int status, boolean isPaidBefore, double amountFromUser, double amountToStore, double amountToGd){
        this.userId = userId;
        this.storeId = storeId;
        this.deliveryId = deliveryId;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.isPaidBefore = isPaidBefore;
        this.amountFromUser = amountFromUser;
        this.amountToStore = amountToStore;
        this.amountToGd = amountToGd;
    }
    public Orders(){

    }
}
