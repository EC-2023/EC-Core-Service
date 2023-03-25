package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="commission")
@Data
public class Commission {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "commissionId", nullable = false)
    private UUID Id;
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
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "commissionByCommissionId")
    private Collection<Store> storesByCommissionId;

 /*   public UUID getCommissionId() {
        return Id;
    }

    public void setCommissionId(UUID commissionId) {
        this.Id = commissionId;
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
        Commission that = (Commission) o;
        return Id == that.Id && Double.compare(that.cost, cost) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(isDeleted, that.isDeleted) && Objects.equals(createAt, that.createAt) && Objects.equals(updateAt, that.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, cost, description, isDeleted, createAt, updateAt);
    }

    public Collection<Store> getStoresByCommissionId() {
        return storesByCommissionId;
    }

    public void setStoresByCommissionId(Collection<Store> storesByCommissionId) {
        this.storesByCommissionId = storesByCommissionId;
    } */
}
