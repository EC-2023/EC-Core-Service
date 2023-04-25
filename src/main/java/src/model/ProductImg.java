package src.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "product_img", catalog = "")
@Data
public class ProductImg {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id_image", nullable = false)
    private UUID Id;
    @Basic
    @Column(name = "product_id", nullable = false)
    private UUID productId;
    @Basic
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
    @Basic
    @Column(name = "location", nullable = true, length = 255)
    private String location;
    @Column(name = "isDeleted", nullable = true)
    private Boolean isDeleted = false;
    @Basic
    @Column(name = "createAt", nullable = false, updatable = false)
    private Date createAt = new Date(new java.util.Date().getTime());
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateAt")
    private Date updateAt = new Date(new java.util.Date().getTime());
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable = false, updatable = false)
    @Where(clause = "is_deleted = false")
    private Product productByProductId;

    public ProductImg () {
    }

    public ProductImg (UUID productId, String fileName, String location) {
        this.productId = productId;
        this.fileName = fileName;
        this.location = location;
    }
}
