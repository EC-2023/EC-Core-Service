package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Data
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
    private int point = 0;
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
    @ManyToOne
    @JoinColumn(name = "store_emp_id", referencedColumnName = "store_id", insertable = false, updatable = false)
    private Store storeByStoreEmpId;
    @OneToMany(mappedBy = "userByUserId", fetch = FetchType.LAZY)
    private Collection<UserAddress> userAddressesByUserId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserFollowProduct> userFollowProductsByUserId;
    @OneToMany(mappedBy = "userByUserId")
    private Collection<UserFollowStore> userFollowStoresByUserId;
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



}
