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
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "commissionByCommissionId")
    private Collection<Store> storesByCommissionId;
    public Commission(UUID id, String name, double cost, String description) {
        Id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
    }
    public Commission() {
    }
}
