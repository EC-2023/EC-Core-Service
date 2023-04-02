package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductCreateDto {

    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "price", required = true)
    private double price;
    @JsonProperty(value = "description", required = true)
    private String description;
    @JsonProperty(value = "promotionalPrice", required = true)
    private double promotionalPrice;
    @JsonProperty(value = "quantity", required = true)
    private int quantity;
    @JsonProperty(value = "sold", required = true)
    private int sold;
    @JsonProperty(value = "isActive", required = true)
    private boolean isActive;
    @JsonProperty(value = "video", required = true)
    private String video;
    @JsonProperty(value = "storeId", required = true)
    private UUID storeId;
    @JsonProperty(value = "rating", required = true)
    private double rating;
    @JsonProperty(value = "categoryId", required = true)
    private UUID categoryId;

}