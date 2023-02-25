package src.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "attribute")
public class Attribute {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "attribute_id", nullable = false)
    private UUID attributeId;
    @Basic
    @Column(name = "name", nullable = true)
    private String name;
    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted;
    @Basic
    @Column(name = "createDate", nullable = false)
    private Date createDate;
    @Basic
    @Column(name = "updateDate", nullable = true)
    private Date updateDate;
    @Basic
    @Column(name = "product_id", nullable = true)
    private UUID product_id;

    @Basic
    @Column(name = "category_id", nullable = true)
    private UUID category_id;

    @OneToMany(mappedBy = "attributeByAttributeId", fetch = FetchType.LAZY)
    private Collection<AttributeValue> attributeValueByAttribute;

    public Product getAttributesByProductId() {
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

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    private Product attributesByProductId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    private Category attributesByCategoryId;


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
        return attributeId == attributes.attributeId && name == attributes.name && product_id == attributes.product_id
                && category_id == attributes.category_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeId, name, product_id, category_id);
    }
}
