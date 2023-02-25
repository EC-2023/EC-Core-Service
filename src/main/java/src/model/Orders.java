package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Orders {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "order_id", nullable = false)
    private UUID orderId;
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
    @Column(name = "createDate", nullable = false)
    private Date createDate;
    @Basic
    @Column(name = "updateDate", nullable = true)
    private Date updateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByUserId;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false, insertable = false, updatable = false)
    private Store storeByStoreId;
    @ManyToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id", nullable = false, insertable = false, updatable = false)
    private Delivery deliveryByDeliveryId;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Collection<OrderItems> item;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isPaidBefore() {
        return isPaidBefore;
    }

    public void setPaidBefore(boolean paidBefore) {
        isPaidBefore = paidBefore;
    }

    public double getAmountFromUser() {
        return amountFromUser;
    }

    public void setAmountFromUser(double amountFromUser) {
        this.amountFromUser = amountFromUser;
    }


    public double getAmountToStore() {
        return amountToStore;
    }

    public void setAmountToStore(double amountToStore) {
        this.amountToStore = amountToStore;
    }

    public double getAmountToGd() {
        return amountToGd;
    }

    public void setAmountToGd(double amountToGd) {
        this.amountToGd = amountToGd;
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
        Orders orders = (Orders) o;
        return orderId == orders.orderId && userId == orders.userId && storeId == orders.storeId && deliveryId == orders.deliveryId && status == orders.status && isPaidBefore == orders.isPaidBefore && Double.compare(orders.amountFromUser, amountFromUser) == 0 && Double.compare(orders.amountToStore, amountToStore) == 0 && Double.compare(orders.amountToGd, amountToGd) == 0 && Objects.equals(address, orders.address) && Objects.equals(phone, orders.phone) && Objects.equals(createDate, orders.createDate) && Objects.equals(updateDate, orders.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, storeId, deliveryId, address, phone, status, isPaidBefore, amountFromUser, amountToStore, amountToGd, createDate, updateDate);
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Store getStoreByStoreId() {
        return storeByStoreId;
    }

    public void setStoreByStoreId(Store storeByStoreId) {
        this.storeByStoreId = storeByStoreId;
    }

    public Delivery getDeliveryByDeliveryId() {
        return deliveryByDeliveryId;
    }

    public void setDeliveryByDeliveryId(Delivery deliveryByDeliveryId) {
        this.deliveryByDeliveryId = deliveryByDeliveryId;
    }

    public Collection<OrderItems> getItem() {
        return item;
    }

    public void setItem(Collection<OrderItems> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return orderId + ", \n"
                + createDate + ",\n";
    }
}
