package src.service.CartItems.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class AttributeValueDto {
    @JsonProperty(value = "name")
    String name;
    @JsonProperty(value = "Id")
    UUID Id;
}
