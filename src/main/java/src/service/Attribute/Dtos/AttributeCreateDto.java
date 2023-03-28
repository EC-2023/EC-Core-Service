package src.service.Attribute.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class AttributeCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "categoryId", required = true)
    public UUID categoryId;
    @JsonProperty(value = "productId", required = true)
    public UUID productId;

}