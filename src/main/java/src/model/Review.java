package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="review")
@Data
public class Review {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "review_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Basic
    @Column(name = "product_id")
    private UUID productId;
    @Basic
    @Column(name = "store_id")
    private UUID storeId;
    @Basic
    @Column(name = "content", nullable = false, length = 255)
    private String content;
    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;
    @Basic
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt")
    private Date createAt;
    @Basic
    @Column(name = "updateAt")
    private Date updateAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    private User userByUserId;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id",insertable=false, updatable=false)
    private Product productByProductId;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id",insertable=false, updatable=false)
    private Store storeByStoreId;
}