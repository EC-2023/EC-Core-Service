package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
public class Product {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "product_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic
    @Column(name = "promotionalPrice", nullable = true, precision = 0)
    private Double promotionalPrice;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic
    @Column(name = "sold", nullable = false)
    private int sold;
    @Basic
    @Column(name = "isActive", nullable = false)
    private boolean isActive;
    @Basic
    @Column(name = "video", nullable = true, length = 255)
    private String video;
    @Basic
    @Column(name = "store_id", nullable = false)
    private UUID storeId;
    @Column(name = "rating", nullable = false, precision = 0)
    private double rating;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = false)
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "dateValidPromote", nullable = true)
    private Date dateValidPromote = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "category_id", nullable = true)
    private UUID categoryId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.EAGER)
    private Collection<CartItems> cartItemsByProductId;
    @OneToMany(mappedBy = "attributesByProductId", fetch = FetchType.EAGER)
    private Collection<Attribute> attributesByProductId;
    @OneToMany(mappedBy = "attributesValueByProductId", fetch = FetchType.EAGER)
    private Collection<AttributeValue> attributesValueByProductId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.EAGER)
    private Collection<OrderItems> orderItemsByProductId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category categoryByCategoryId;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false, insertable = false, updatable = false)
    private Store storeByStoreId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<ProductImg> productImgsByProductId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.EAGER)
    private Collection<Review> reviewsByProductId;
    @OneToMany(mappedBy = "productByProductId")
    private Collection<UserFollowProduct> userFollowProductsByProductId;
}
