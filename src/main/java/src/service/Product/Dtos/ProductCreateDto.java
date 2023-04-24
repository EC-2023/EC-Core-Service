package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ProductCreateDto {
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
    @JsonProperty(value = "video")
    private String video;
    @JsonProperty(value = "storeId")
    private UUID storeId;
    @JsonProperty(value = "rating")
    private double rating;
}