package src.service.AttributeValue.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class AttributeValueCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "attributeId", required = true)
    public UUID attributeId;
    @JsonProperty(value = "orderItemId", required = true)
    public UUID orderItemId;
    @JsonProperty(value = "productId", required = true)
    public UUID productId;
}