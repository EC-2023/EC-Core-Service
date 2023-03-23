package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user_level")
@Data
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
    @Column(name = "isDeleted")
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", updatable = false)
    private Date createAt = new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt")
    private Date updateAt = new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "userLevelByUserLevelId")
    private Collection<User> usersByUserLevelId;

    public UserLevel(UUID id, String name, int minPoint, Double discount) {
        Id = id;
        this.name = name;
        this.minPoint = minPoint;
        this.discount = discount;
    }
    public UserLevel() {
    }
}
