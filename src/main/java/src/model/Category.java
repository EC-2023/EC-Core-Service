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
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
//    @ManyToOne
//    @JoinColumn(name = "parent_category_id", referencedColumnName = "category_id", insertable=false, updatable=false)
//    private Category categoryByParentCategoryId;
//    @OneToMany(mappedBy = "categoryByParentCategoryId")
//    private Collection<Category> categoriesByCategoryId;
    @OneToMany(mappedBy = "categoryByCategoryId")
    private Collection<Product> productsByCategoryId;

    @OneToMany(mappedBy = "attributesByCategoryId")
    private Collection<Attribute> attributesByCategoryId;

 /*   public UUID getCategoryId() {
        return Id;
    }

    public void setCategoryId(UUID Id) {
        this.Id = Id;
    }

//    public Integer getParentCategoryId() {
//        return parentCategoryId;
//    }

//    public void setParentCategoryId(Integer parentCategoryId) {
//        this.parentCategoryId = parentCategoryId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        Category category = (Category) o;
        return Id == category.Id && Objects.equals(name, category.name) && Objects.equals(image, category.image) && Objects.equals(isDeleted, category.isDeleted) && Objects.equals(createAt, category.createAt) && Objects.equals(updateAt, category.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, image, isDeleted, createAt, updateAt);
    }

//    public Category getCategoryByParentCategoryId() {
//        return categoryByParentCategoryId;
//    }
//
//    public void setCategoryByParentCategoryId(Category categoryByParentCategoryId) {
//        this.categoryByParentCategoryId = categoryByParentCategoryId;
//    }

//    public Collection<Category> getCategoriesByCategoryId() {
//        return categoriesByCategoryId;
//    }
//
//    public void setCategoriesByCategoryId(Collection<Category> categoriesByCategoryId) {
//        this.categoriesByCategoryId = categoriesByCategoryId;
//    }

    public Collection<Product> getProductsByCategoryId() {
        return productsByCategoryId;
    }

    public void setProductsByCategoryId(Collection<Product> productsByCategoryId) {
        this.productsByCategoryId = productsByCategoryId;
    } */
}
