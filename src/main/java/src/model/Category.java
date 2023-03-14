package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "category")
@Data
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
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "categoryByCategoryId")
    private Collection<Product> productsByCategoryId;
    @OneToMany(mappedBy = "attributesByCategoryId")
    private Collection<Attribute> attributesByCategoryId;
}
