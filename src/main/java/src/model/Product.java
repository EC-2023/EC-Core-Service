package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "product_id", nullable = false)
    private UUID productId;
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
    @Column(name = "createDate", nullable = false)
    private Date createDate;
    @Basic
    @Column(name = "updateDate", nullable = true)
    private Date updateDate;
    @Basic
    @Column(name = "category_id", nullable = true)
    private UUID categoryId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.LAZY)
    private Collection<CartItems> cartItemsByProductId;
    @OneToMany(mappedBy = "attributesByProductId", fetch = FetchType.LAZY)
    private Collection<Attribute> attributesByProductId;
    @OneToMany(mappedBy = "attributesValueByProductId", fetch = FetchType.LAZY)
    private Collection<AttributeValue> attributesValueByProductId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.LAZY)
    private Collection<OrderItems> orderItemsByProductId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category categoryByCategoryId;
    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false, insertable = false, updatable = false)
    private Store storeByStoreId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<ProductImg> productImgsByProductId;
    @OneToMany(mappedBy = "productByProductId", fetch = FetchType.LAZY)
    private Collection<Review> reviewsByProductId;
    @OneToMany(mappedBy = "productByProductId")
    private Collection<UserFollowProduct> userFollowProductsByProductId;

    public Product() {
    }

    public Product(String name, String description, double price, Double promotionalPrice,
                   int quantity, boolean isActive, String video, UUID storeId, Date createDate, UUID categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.promotionalPrice = promotionalPrice;
        this.quantity = quantity;
        this.isActive = isActive;
        this.video = video;
        this.storeId = storeId;
        this.createDate = createDate;
        this.categoryId = categoryId;
    }

    public Store getStoreByStoreId() {
        return storeByStoreId;
    }

    public void setStoreByStoreId(Store storeByStoreId) {
        this.storeByStoreId = storeByStoreId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getPromotionalPrice() {
        return promotionalPrice;
    }

    public void setPromotionalPrice(Double promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Double.compare(product.price, price) == 0 && quantity == product.quantity && sold == product.sold && isActive == product.isActive && storeId == product.storeId && Double.compare(product.rating, rating) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(promotionalPrice, product.promotionalPrice) && Objects.equals(video, product.video) && Objects.equals(createDate, product.createDate) && Objects.equals(updateDate, product.updateDate) && Objects.equals(categoryId, product.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, promotionalPrice, quantity, sold, isActive, video, storeId, rating, createDate, updateDate, categoryId);
    }

    public Collection<CartItems> getCartItemsByProductId() {
        return cartItemsByProductId;
    }

    public void setCartItemsByProductId(Collection<CartItems> cartItemsByProductId) {
        this.cartItemsByProductId = cartItemsByProductId;
    }

    public Collection<OrderItems> getOrderItemsByProductId() {
        return orderItemsByProductId;
    }

    public void setOrderItemsByProductId(Collection<OrderItems> orderItemsByProductId) {
        this.orderItemsByProductId = orderItemsByProductId;
    }

    public Category getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(Category categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    public Collection<ProductImg> getProductImgsByProductId() {
        return productImgsByProductId;
    }

    public void setProductImgsByProductId(Collection<ProductImg> productImgsByProductId) {
        this.productImgsByProductId = productImgsByProductId;
    }

    public Collection<Review> getReviewsByProductId() {
        return reviewsByProductId;
    }

    public void setReviewsByProductId(Collection<Review> reviewsByProductId) {
        this.reviewsByProductId = reviewsByProductId;
    }

    public Collection<UserFollowProduct> getUserFollowProductsByProductId() {
        return userFollowProductsByProductId;
    }

    public void setUserFollowProductsByProductId(Collection<UserFollowProduct> userFollowProductsByProductId) {
        this.userFollowProductsByProductId = userFollowProductsByProductId;
    }
}
