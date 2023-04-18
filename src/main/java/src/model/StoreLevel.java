package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "store_level")
@Data
public class StoreLevel {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "store_level_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "minPoint", nullable = false)
    private int minPoint;
    @Basic
    @Column(name = "discount", precision = 0)
    private Double discount;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted= false;
    @Basic
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "storeLevel",fetch = FetchType.EAGER)
    private Collection<Store> stores;
}
