package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.service.Attribute.Dtos.AttributeDto;
import src.service.Review.Dtos.ReviewDto;
import src.service.Store.Dtos.CategoryProductDto;
import src.service.Store.Dtos.StoreProductDto;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ProductDetailDto {
    @JsonProperty(value = "Id")
    public UUID Id;
    @JsonProperty(value = "createAt")
    public Date createAt;
    @JsonProperty(value = "updateAt")
    public Date updateAt;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted = false;
    @JsonProperty(value = "isActive")
    public boolean isActive;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "price")
    private double price;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "promotionalPrice")
    private double promotionalPrice;
    @JsonProperty(value = "dateValidPromote")
    private Date dateValidPromote;
    @JsonProperty(value = "quantity")
    private int quantity;
    @JsonProperty(value = "sold")
    private int sold;
    @JsonProperty(value = "video")
    private String video;
    @JsonProperty(value = "attributes")
    public List<AttributeDto> attributesByProductId;
    @JsonProperty(value = "reviews")
    public Collection<ReviewDto> reviewsByProductId;
    @JsonProperty(value = "store")
    public StoreProductDto storeByStoreId;
    @JsonProperty(value = "category")
    public CategoryProductDto categoryByCategoryId;
    @JsonProperty(value = "images")
    private Collection<ProductImageDto> productImgsByProductId;
    @JsonProperty(value = "categoryId", required = true)
    private UUID categoryId;
}
