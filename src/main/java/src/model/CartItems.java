package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cart_items",  catalog = "")
@Data
public class CartItems {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;
    @Basic
    @Column(name = "product_id", nullable = false)
    private UUID productId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
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
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id", nullable = false, insertable=false, updatable=false)
    private Cart cartByCartId;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable=false, updatable=false)
    private Product productByProductId;
}
