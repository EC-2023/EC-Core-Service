package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cart")
@Data
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
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByUserId;
    @OneToMany(mappedBy = "cartByCartId", fetch = FetchType.LAZY)
    private Collection<CartItems> cartItemsByCartId;

    public Cart() {

    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItems i : this.cartItemsByCartId) {
            total += (i.getQuantity() * i.getProductByProductId().getPrice());
        }
        return total;
    }
    public Cart(UUID userId) {
        this.userId = userId;
    }
}
