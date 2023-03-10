package src.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "user_level_id", nullable = false)
    private UUID userLevelId;
    @Basic
    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName;
    @Basic
    @Column(name = "middle_name", length = 32)
    private String middleName;
    @Basic
    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;
    @Basic
    @Column(name = "id_card", nullable = false, length = 12)
    private String idCard;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "is_required_verify", nullable = false)
    private boolean isRequiredVerify = false;
    @Basic
    @Column(name = "verified_at")
    private Date verifiedAt;
    @Basic
    @Column(name = "last_login")
    private Date lastLogin;
    @Basic
    @Column(name = "hashed_password", nullable = false, length = 100)
    private String hashedPassword;
    @Basic
    @Column(name = "avatar", length = 255)
    private String avatar;
    @Basic
    @Column(name = "point", nullable = false)
    private int point;
    @Basic
    @Column(name = "e_wallet", nullable = false, precision = 0)
    private double eWallet;
    @Basic
    @Column(name = "created_at")
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updated_at")
    private Date updateAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "role_id", nullable = false)
    private UUID roleId;
    @Basic
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
    @Basic
    @Column(name = "store_emp_id", nullable = true)
    private UUID storeEmpId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<Cart> cartsByUserId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<Orders> ordersByUserId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<Review> reviewsByUserId;
    @OneToMany(mappedBy = "userByOwnId")
    private Collection<Store> storesByUserId;
    @ManyToOne
    @JoinColumn(name = "user_level_id", referencedColumnName = "user_level_id", nullable = false, insertable = false, updatable = false)
    private UserLevel userLevelByUserLevelId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false, insertable = false, updatable = false)
    private Role roleByRoleId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roleByRoleId.getName()));
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted;
    }

    @ManyToOne
    @JoinColumn(name = "store_emp_id", referencedColumnName = "store_id", insertable = false, updatable = false)
    private Store storeByStoreEmpId;
    @OneToMany(mappedBy = "userByUserId", fetch = FetchType.LAZY)
    private Collection<UserAddress> userAddressesByUserId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserFollowProduct> userFollowProductsByUserId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserFollowStore> userFollowStoresByUserId;

    public UUID getUserId() {
        return Id;
    }

    public void setUserId(UUID Id) {
        this.Id = Id;
    }

    public UUID getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(UUID userLevelId) {
        this.userLevelId = userLevelId;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isRequiredVerify() {
        return isRequiredVerify;
    }

    public void setRequiredVerify(boolean requiredVerify) {
        isRequiredVerify = requiredVerify;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public double geteWallet() {
        return eWallet;
    }

    public void seteWallet(double eWallet) {
        this.eWallet = eWallet;
    }

    public Date getCreateDate() {
        return createAt;
    }

    public void setCreateDate(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdatedAt() {
        return updateAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updateAt = updatedAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UUID getStoreEmpId() {
        return storeEmpId;
    }

    public void setStoreEmpId(UUID storeEmpId) {
        this.storeEmpId = storeEmpId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Id == user.Id && userLevelId == user.userLevelId &&
                isRequiredVerify == user.isRequiredVerify &&
                point == user.point
                && Double.compare(user.eWallet, eWallet) == 0
                && roleId == user.roleId
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(middleName, user.middleName)
                && Objects.equals(displayName, user.displayName)
                && Objects.equals(idCard, user.idCard)
                && Objects.equals(email, user.email)
                && Objects.equals(hashedPassword, user.hashedPassword)
                && Objects.equals(avatar, user.avatar)
                && Objects.equals(createAt, user.createAt)
                && Objects.equals(updateAt, user.updateAt)
                && Objects.equals(verifiedAt, user.verifiedAt)
                && Objects.equals(lastLogin, user.lastLogin)
                && Objects.equals(isDeleted, user.isDeleted)
                && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(storeEmpId, user.storeEmpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, userLevelId, firstName, lastName, isRequiredVerify, middleName, displayName,
                idCard, email, middleName, displayName, hashedPassword, avatar, point, eWallet,
                createAt, updateAt, isDeleted, roleId, phoneNumber, storeEmpId);
    }

    public Collection<Cart> getCartsByUserId() {
        return cartsByUserId;
    }

    public void setCartsByUserId(Collection<Cart> cartsByUserId) {
        this.cartsByUserId = cartsByUserId;
    }

    public Collection<Orders> getOrdersByUserId() {
        return ordersByUserId;
    }

    public void setOrdersByUserId(Collection<Orders> ordersByUserId) {
        this.ordersByUserId = ordersByUserId;
    }

    public Collection<Review> getReviewsByUserId() {
        return reviewsByUserId;
    }

    public void setReviewsByUserId(Collection<Review> reviewsByUserId) {
        this.reviewsByUserId = reviewsByUserId;
    }

    public Collection<Store> getStoresByUserId() {
        return storesByUserId;
    }

    public void setStoresByUserId(Collection<Store> storesByUserId) {
        this.storesByUserId = storesByUserId;
    }

    public UserLevel getUserLevelByUserLevelId() {
        return userLevelByUserLevelId;
    }

    public void setUserLevelByUserLevelId(UserLevel userLevelByUserLevelId) {
        this.userLevelByUserLevelId = userLevelByUserLevelId;
    }

    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }

    public Store getStoreByStoreEmpId() {
        return storeByStoreEmpId;
    }

    public void setStoreByStoreEmpId(Store storeByStoreEmpId) {
        this.storeByStoreEmpId = storeByStoreEmpId;
    }

    public Collection<UserAddress> getUserAddressesByUserId() {
        return userAddressesByUserId;
    }

    public void setUserAddressesByUserId(Collection<UserAddress> userAddressesByUserId) {
        this.userAddressesByUserId = userAddressesByUserId;
    }

    public Collection<UserFollowProduct> getUserFollowProductsByUserId() {
        return userFollowProductsByUserId;
    }

    public void setUserFollowProductsByUserId(Collection<UserFollowProduct> userFollowProductsByUserId) {
        this.userFollowProductsByUserId = userFollowProductsByUserId;
    }

    public Collection<UserFollowStore> getUserFollowStoresByUserId() {
        return userFollowStoresByUserId;
    }

    public void setUserFollowStoresByUserId(Collection<UserFollowStore> userFollowStoresByUserId) {
        this.userFollowStoresByUserId = userFollowStoresByUserId;
    }
}
