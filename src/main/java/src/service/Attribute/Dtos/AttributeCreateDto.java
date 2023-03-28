package src.service.Attribute.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttributeCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;

}