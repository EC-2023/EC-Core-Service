package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AttributeValueUpdate {
    @JsonProperty(value = "name")
    String name;
    @JsonProperty(value = "Id")
    String Id;
    @JsonProperty(value = "attributeId")
    String attributeId;
    @JsonProperty(value = "values")
    List<AttributeValueUpdate> values = new ArrayList<>();
}
