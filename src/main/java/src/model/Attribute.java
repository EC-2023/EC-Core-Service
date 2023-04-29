package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "attribute")
@Data
public class    Attribute {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "attribute_id", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "name", nullable = true)
    private String name;
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
    @Basic
    @Column(name = "product_id", nullable = true)
    private UUID product_id;
    @Basic
    @Column(name = "category_id", nullable = true)
    private UUID category_id;
    @OneToMany(mappedBy = "attributeByAttributeId", fetch = FetchType.EAGER)
    @Where(clause = "is_deleted = false")
    private Collection<AttributeValue> attributeValueByAttribute;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    private Product attributesByProductId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false, insertable = false, updatable = false)
    private Category attributesByCategoryId;

    public Attribute() {
    }

    public Attribute(UUID productId, String name) {
        this.product_id = productId;
        this.name = name;
    }
}


