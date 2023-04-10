
package src.service.AttributeValue.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttributeValueInputDto {
    @JsonProperty(value = "name", required = true)
    private String name;
}
