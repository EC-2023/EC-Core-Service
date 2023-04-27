package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Data
public class ProductStoreDto {
    @JsonProperty(value = "Id")
    public UUID Id;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted = false;
    @JsonProperty(value = "isActive")
    public boolean isActive;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "price")
    private double price;
    @JsonProperty(value = "quantity")
    private int quantity;
    @JsonProperty(value = "sold")
    private int sold;
    @JsonProperty(value = "images")
    private Collection<ProductImageDto> productImgsByProductId;
}
