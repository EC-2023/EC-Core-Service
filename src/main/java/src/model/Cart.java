package src.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cart")
public class Cart {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "cart_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted;
    @Basic
    @Column(name = "createAt", nullable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByUserId;
    @OneToMany(mappedBy = "cartByCartId", fetch = FetchType.LAZY)
    private Collection<CartItems> cartItemsByCartId;

    public UUID getCartId() {
        return Id;
    }

    public void setCartId(UUID Id) {
        this.Id = Id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
        Cart cart = (Cart) o;
        return Id == cart.Id && userId == cart.userId && Objects.equals(isDeleted, cart.isDeleted) && Objects.equals(createAt, cart.createAt) && Objects.equals(updateAt, cart.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, userId, isDeleted, createAt, updateAt);
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Collection<CartItems> getCartItemsByCartId() {
        return cartItemsByCartId;
    }

    public void setCartItemsByCartId(Collection<CartItems> cartItemsByCartId) {
        this.cartItemsByCartId = cartItemsByCartId;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItems i : this.cartItemsByCartId) {
            total += (i.getQuantity() * i.getProductByProductId().getPrice());
        }
        return total;
    }
}
