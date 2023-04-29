package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class AttributeValueUpdate {
    @JsonProperty(value = "name")
    String name;
    @JsonProperty(value = "Id")
    UUID Id;
    @JsonProperty(value = "attributeId")
    String attributeId;
    @JsonProperty(value = "values")
    List<AttributeValueUpdate> values = new ArrayList<>();
}
