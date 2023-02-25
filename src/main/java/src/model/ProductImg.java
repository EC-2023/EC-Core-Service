package src.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product_img",  catalog = "")
public class ProductImg {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id_image", nullable = false)
    private UUID idImage;
    @Basic
    @Column(name = "product_id", nullable = false)
    private UUID productId;
    @Basic
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
    @Basic
    @Column(name = "location", nullable = true, length = 255)
    private String location;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable=false, updatable=false)
    private Product productByProductId;

    public UUID getIdImage() {
        return idImage;
    }

    public void setIdImage(UUID idImage) {
        this.idImage = idImage;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImg that = (ProductImg) o;
        return idImage == that.idImage && productId == that.productId && Objects.equals(fileName, that.fileName) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImage, productId, fileName, location);
    }

    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }
}
