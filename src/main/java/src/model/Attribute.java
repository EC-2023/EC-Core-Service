package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "attribute")
@Data
public class Attribute {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "attribute_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = true)
    private String name;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted;
    @Basic
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());
    @Basic
    @Column(name = "product_id", nullable = true)
    private UUID productId;
    @Basic
    @Column(name = "category_id", nullable = true)
    private UUID categoryId;
    @OneToMany(mappedBy = "attributeByAttributeId", fetch = FetchType.LAZY)
    private Collection<AttributeValue> attributeValueByAttribute;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    private Product attributesByProductId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    private Category attributesByCategoryId;

  /*  public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

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

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public UUID getCategory_id() {
        return category_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }
*/


 /*   public Product getAttributesByProductId() {
        return attributesByProductId;
    }
    public void setAttributesByProductId(Product attributesByProductId) {
        this.attributesByProductId = attributesByProductId;
    }
    public Category getAttributesByCategoryId() {
        return attributesByCategoryId;
    }
    public void setAttributesByCategoryId(Category attributesByCategoryId) {
        this.attributesByCategoryId = attributesByCategoryId;
    }

    public Collection<AttributeValue> getAttributeValueByAttribute() {
        return attributeValueByAttribute;
    }

    public void setAttributeValueByAttribute(Collection<AttributeValue> attributeValueByAttribute) {
        this.attributeValueByAttribute = attributeValueByAttribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attributes = (Attribute) o;
        return Id == attributes.Id && name == attributes.name && product_id == attributes.product_id
                && category_id == attributes.category_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, product_id, category_id);
    } */

}
