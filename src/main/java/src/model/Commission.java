package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="commission")
public class Commission {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "commissionId", nullable = false)
    private UUID commissionId;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "cost", nullable = false, precision = 0)
    private double cost;
    @Basic
    @Column(name = "description", nullable = false, length = 255)
    private String description;
    @Basic
    @Column(name = "isDeleted", nullable = true)

    private Boolean isDeleted = false;

    @Basic
    @Column(name = "createDate", nullable = false, updatable = false)
    private Date createDate = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateDate", nullable = true)
    private Date updateDate;
    @OneToMany(mappedBy = "commissionByCommissionId")
    private Collection<Store> storesByCommissionId;

    public UUID getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(UUID commissionId) {
        this.commissionId = commissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commission that = (Commission) o;
        return commissionId == that.commissionId && Double.compare(that.cost, cost) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(isDeleted, that.isDeleted) && Objects.equals(createDate, that.createDate) && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commissionId, name, cost, description, isDeleted, createDate, updateDate);
    }

    public Collection<Store> getStoresByCommissionId() {
        return storesByCommissionId;
    }

    public void setStoresByCommissionId(Collection<Store> storesByCommissionId) {
        this.storesByCommissionId = storesByCommissionId;
    }
}
