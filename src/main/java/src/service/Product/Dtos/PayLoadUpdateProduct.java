package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.model.ProductImg;

import java.util.ArrayList;
import java.util.List;

@Data
public class PayLoadUpdateProduct {
    @JsonProperty(value = "info")
    private BasicDataProduct info;
    @JsonProperty(value = "images")
    private List<ProductImg> images = new ArrayList<>();
    @JsonProperty(value = "attributes")
    private List<AttributeUpdate> attributes = new ArrayList<>();
    @JsonProperty(value = "attributeValues")
    private List<AttributeUpdate> attributeValues = new ArrayList<>();

}
