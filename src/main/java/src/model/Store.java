package src.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "store")
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
    @Column(name = "bio", nullable = false, length = 255)
    private String bio;
    @Basic
    @Column(name = "isActive", nullable = false)
    private boolean isActive = true;
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

    public void setPoint(int point) {
        this.point = point;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted= false;
    @Basic
    @Column(name = "createAt", nullable = true)
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "storeByStoreId")
    private Collection<Orders> ordersByStoreId;
    @OneToMany(mappedBy = "storeByStoreId")
    private Collection<Review> reviewsByStoreId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownId", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User userByOwnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_level_id")
    private StoreLevel storeLevel;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commissionId", referencedColumnName = "commissionId", nullable = false, insertable = false, updatable = false)
    private Commission commissionByCommissionId;
    @OneToMany(mappedBy = "storeByStoreEmpId")
    private Collection<User> usersByStoreId;
    @OneToMany(mappedBy = "storeByStoreId", fetch = FetchType.LAZY)
    private Collection<UserFollowStore> userFollowStoresByStoreId;

    @OneToMany(mappedBy = "storeByStoreId", fetch = FetchType.LAZY)
    private Collection<Product> productsByStoreId;

    public Collection<Product> getProductsByStoreId() {
        return productsByStoreId;
    }

    public void setProductsByStoreId(Collection<Product> productsByStoreId) {
        this.productsByStoreId = productsByStoreId;
    }

    public UUID getStoreId() {
        return Id;
    }

    public void setStoreId(UUID storeId) {
        this.Id = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwnId() {
        return ownId;
    }

    public void setOwnId(UUID ownId) {
        this.ownId = ownId;
    }

    public UUID getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(UUID commissionId) {
        this.commissionId = commissionId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFeaturedImages() {
        return featuredImages;
    }

    public void setFeaturedImages(String featuredImages) {
        this.featuredImages = featuredImages;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double geteWallet() {
        return eWallet;
    }

    public void seteWallet(Double eWallet) {
        this.eWallet = eWallet;
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
        Store store = (Store) o;
        return Id == store.Id && ownId == store.ownId && commissionId == store.commissionId && isActive == store.isActive && Objects.equals(name, store.name) && Objects.equals(bio, store.bio) && Objects.equals(avatar, store.avatar) && Objects.equals(cover, store.cover) && Objects.equals(featuredImages, store.featuredImages) && Objects.equals(point, store.point) && Objects.equals(rating, store.rating) && Objects.equals(eWallet, store.eWallet) && Objects.equals(createAt, store.createAt) && Objects.equals(updateAt, store.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, ownId, commissionId, bio, isActive, avatar, cover, featuredImages, point, rating, eWallet, isDeleted, createAt, updateAt);
    }

    public Collection<Orders> getOrdersByStoreId() {
        return ordersByStoreId;
    }

    public void setOrdersByStoreId(Collection<Orders> ordersByStoreId) {
        this.ordersByStoreId = ordersByStoreId;
    }

    public Collection<Review> getReviewsByStoreId() {
        return reviewsByStoreId;
    }

    public void setReviewsByStoreId(Collection<Review> reviewsByStoreId) {
        this.reviewsByStoreId = reviewsByStoreId;
    }

    public User getUserByOwnId() {
        return userByOwnId;
    }

    public void setUserByOwnId(User userByOwnId) {
        this.userByOwnId = userByOwnId;
    }

    public Commission getCommissionByCommissionId() {
        return commissionByCommissionId;
    }

    public void setCommissionByCommissionId(Commission commissionByCommissionId) {
        this.commissionByCommissionId = commissionByCommissionId;
    }


    public Collection<User> getUsersByStoreId() {
        return usersByStoreId;
    }

    public void setUsersByStoreId(Collection<User> usersByStoreId) {
        this.usersByStoreId = usersByStoreId;
    }

    public Collection<UserFollowStore> getUserFollowStoresByStoreId() {
        return userFollowStoresByStoreId;
    }

    public void setUserFollowStoresByStoreId(Collection<UserFollowStore> userFollowStoresByStoreId) {
        this.userFollowStoresByStoreId = userFollowStoresByStoreId;
    }

    public StoreLevel getStoreLevel() {
        return storeLevel;
    }

    public void setStoreLevel(StoreLevel storeLevel) {
        this.storeLevel = storeLevel;
    }
}
