package src.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_level", catalog = "")
public class UserLevel {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "user_level_id")
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Basic
    @Column(name = "min_point", nullable = false)
    private int minPoint;
    @Basic
    @Column(name = "discount", precision = 0)
    private Double discount;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = true)
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt = new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "userLevelByUserLevelId")
    private Collection<User> usersByUserLevelId;
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
    public UUID getUserLevelId() {
        return Id;
    }

    public void setUserLevelId(UUID Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(int minPoint) {
        this.minPoint = minPoint;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean delete) {
        isDeleted = delete;
    }

    public Date getCreateDate() {
        return createAt;
    }

    public void setCreateDate(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLevel level = (UserLevel) o;
        return Id == level.Id && minPoint == level.minPoint && Objects.equals(name, level.name) && Objects.equals(discount, level.discount) && Objects.equals(isDeleted, level.isDeleted) && Objects.equals(createAt, level.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, minPoint, discount, isDeleted, createAt);
    }

    public Collection<User> getUsersByUserLevelId() {
        return usersByUserLevelId;
    }

    public void setUsersByUserLevelId(Collection<User> usersByUserLevelId) {
        this.usersByUserLevelId = usersByUserLevelId;
    }
}
