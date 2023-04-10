
package src.service.Category.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryInputDto {
    @JsonProperty(value = "name", required = true)
    private String name;
}
