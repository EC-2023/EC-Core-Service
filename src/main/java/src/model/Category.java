package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "category")
@Data
//@DynamicUpdate
//@DynamicInsert
public class Category {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "category_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "parent_category_id", nullable = true)
    private UUID parentCategoryId = null;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "image", nullable = true, length = 255)
    private String image;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = false)
    private Date createAt = new Date(new java.util.Date().getTime());
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt = new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "categoryByCategoryId")
    @Where(clause = "isDeleted = false")
    private Collection<Product> productsByCategoryId;
    @OneToMany(mappedBy = "attributesByCategoryId")
    @Where(clause = "isDeleted = false")
    private Collection<Attribute> attributesByCategoryId;
    @ManyToOne(fetch = FetchType.EAGER)
    @Where(clause = "isDeleted = false")
    @JoinColumn(name = "parent_category_id",insertable = false, updatable = false)
    private Category parentCategory;
}
