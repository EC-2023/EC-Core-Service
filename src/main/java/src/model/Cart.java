package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cart")
//@DynamicInsert
//@DynamicUpdate
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
    @Column(name = "store_id", nullable = false)
    private UUID storeId;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
   @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt = new Date(new java.util.Date().getTime());
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
   //  @Where(clause = "isDeleted = false")
    private User userByUserId;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false, insertable = false, updatable = false)
   //  @Where(clause = "isDeleted = false")
    private Store storeByStoreId;
    @OneToMany(mappedBy = "cartByCartId", fetch = FetchType.EAGER)
    @Where(clause = "is_deleted = false")
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

    public Cart(UUID userId, UUID storeId) {
        this.userId = userId;
        this.storeId = storeId;
    }
}
