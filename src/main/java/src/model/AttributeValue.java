package src.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "attribute_value")
@Data
public class AttributeValue {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "attribute_value_id", nullable = false)
    private UUID Id;

    @Basic
    @Column(name = "name", nullable = true)
    private String name;

    @Basic
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted;

    @Basic
    @Column(name = "createAt", nullable = false)
    private Date createAt= new Date(new java.util.Date().getTime());

    @Basic
    @Column(name = "updateAt", nullable = true)
    private Date updateAt= new Date(new java.util.Date().getTime());

    @Basic
    @Column(name = "attribute_id", nullable = true)
    private UUID attribute_id;

    @Basic
    @Column(name = "product_id", nullable = true)
    private UUID product_id;

    @Basic
    @Column(name = "order_item_id", nullable = true)
    private UUID orderItem_id;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id", nullable = true, insertable = false, updatable = false)
    private Attribute attributeByAttributeId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = true, insertable = false, updatable = false)
    private Product attributesValueByProductId;

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
    private OrderItems attributesValueByOrderItemId;

}
