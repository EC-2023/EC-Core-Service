package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ProductCreatePayload {
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "price", required = true)
    private double price;
    @JsonProperty(value = "description", required = true)
    private String description;
    @JsonProperty(value = "promotionalPrice")
    private double promotionalPrice;
    @JsonProperty(value = "quantity", required = true)
    private int quantity;
    @JsonProperty(value = "video", required = true)
    private String video;
    @JsonProperty(value = "categoryId", required = true)
    private UUID categoryId;
    @JsonProperty(value = "dateValidPromote")
    private Date dateValidPromote;
    @JsonProperty(value = "images")
    private List<String> images = new ArrayList<>();
    @JsonProperty(value = "attributes")
    private List<AttributePayload> attributes = new ArrayList<>();
}


