package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cart")
public class Cart {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;
    @Basic
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted;
    @Basic
    @Column(name = "createDate", nullable = false)
    private Date createDate;
    @Basic
    @Column(name = "updateDate", nullable = true)
    private Date updateDate;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByUserId;
    @OneToMany(mappedBy = "cartByCartId", fetch = FetchType.LAZY)
    private Collection<CartItems> cartItemsByCartId;

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
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
        Cart cart = (Cart) o;
        return cartId == cart.cartId && userId == cart.userId && Objects.equals(isDeleted, cart.isDeleted) && Objects.equals(createDate, cart.createDate) && Objects.equals(updateDate, cart.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, isDeleted, createDate, updateDate);
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
