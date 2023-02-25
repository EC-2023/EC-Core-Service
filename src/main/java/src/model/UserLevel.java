package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_level",  catalog = "")
public class UserLevel {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "user_level_id", nullable = false)
    private UUID userLevelId;
    @Basic
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Basic
    @Column(name = "min_point", nullable = false)
    private int minPoint;
    @Basic
    @Column(name = "discount", nullable = true, precision = 0)
    private Double discount;
    @Basic
    @Column(name = "isDelete", nullable = true)
    private Boolean isDelete= false;
    @Basic
    @Column(name = "createDate", nullable = true)
    private Date createDate = new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "userLevelByUserLevelId")
    private Collection<User> usersByUserLevelId;

    public UUID getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(UUID userLevelId) {
        this.userLevelId = userLevelId;
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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLevel level = (UserLevel) o;
        return userLevelId == level.userLevelId && minPoint == level.minPoint && Objects.equals(name, level.name) && Objects.equals(discount, level.discount) && Objects.equals(isDelete, level.isDelete) && Objects.equals(createDate, level.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLevelId, name, minPoint, discount, isDelete, createDate);
    }

    public Collection<User> getUsersByUserLevelId() {
        return usersByUserLevelId;
    }

    public void setUsersByUserLevelId(Collection<User> usersByUserLevelId) {
        this.usersByUserLevelId = usersByUserLevelId;
    }
}
