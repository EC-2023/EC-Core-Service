package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttributeValueUpdate {
    @JsonProperty(value = "name")
    String name;
    @JsonProperty(value = "Id")
    String Id;
}
