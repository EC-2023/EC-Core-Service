package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "store")
@Data
//@DynamicUpdate
//@DynamicInsert
public class Store {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "store_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "ownId", nullable = false)
    private UUID ownId;
    @Basic
    @Column(name = "commissionId", nullable = false)
    private UUID commissionId;
    @Basic
    @Column(name = "store_level_id", nullable = true)
    private UUID storeLevelId;
    @Basic
    @Column(name = "bio", nullable = false, length = 255)
    private String bio;
    @Basic
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic
    @Column(name = "isActive", nullable = false)
    private boolean isActive = false;
    @Basic
    @Column(name = "avatar", nullable = true, length = 255)
    private String avatar;
    @Basic
    @Column(name = "cover", nullable = true, length = 255)
    private String cover;
    @Basic
    @Column(name = "featured_images", nullable = true, length = 255)
    private String featuredImages = "feature.jpg";
    @Basic
    @Column(name = "point", nullable = true)
    private int point = 0;
    @Basic
    @Column(name = "rating", nullable = true)
    private int rating = 0;
    @Basic
    @Column(name = "e_wallet", nullable = true, precision = 0)
    private Double eWallet = 0D;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = true)
    private Date createAt = new Date(new java.util.Date().getTime());
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt = new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "storeByStoreId")
    private Collection<Orders> ordersByStoreId;
    @OneToMany(mappedBy = "storeByStoreId")
    private Collection<Review> reviewsByStoreId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownId", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByOwnId;
    @ManyToOne()
    @JoinColumn(name = "store_level_id", referencedColumnName = "store_level_id", nullable = false, insertable = false, updatable = false)
    private StoreLevel storeLevelByStoreLevelId;
    @ManyToOne()
    @JoinColumn(name = "commissionId", referencedColumnName = "commissionId", nullable = false, insertable = false, updatable = false)
    private Commission commissionByCommissionId;
    @OneToMany(mappedBy = "storeByStoreEmpId")
    private Collection<User> usersByStoreId;
    @OneToMany(mappedBy = "storeByStoreId")
    private Collection<UserFollowStore> userFollowStoresByStoreId;
    @OneToMany(mappedBy = "storeByStoreId")
    private Collection<Product> productsByStoreId;
}
