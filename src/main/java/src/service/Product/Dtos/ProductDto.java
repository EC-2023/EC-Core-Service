
package src.service.Product.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.service.ProductImg.Dtos.ProductImgDto;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
public class ProductDto extends ProductUpdateDto {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "createAt", required = true)
    public Date createAt;
    @JsonProperty(value = "updateAt", required = true)
    public Date updateAt;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted = false;
    @JsonProperty(value = "isActive", required = true)
    public boolean isActive;
    @JsonProperty(value = "images")
    public Collection<ProductImgDto> productImgsByProductId;

//    @JsonProperty(value = "attributes")
//    public List<AttributeDto> attributesByProductId;
//
//    @JsonProperty(value = "attributeValues")
//    public Collection<AttributeValueDto> attributesValueByProductId;

//    @JsonProperty(value = "reviews")
//    public Collection<ReviewDto> reviewsByProductId;
//
//    @JsonProperty(value = "store")
//    public StoreProductDto storeByStoreId;
//
//    @JsonProperty(value = "category")
//    public CategoryProductDto categoryByCategoryId;
}

