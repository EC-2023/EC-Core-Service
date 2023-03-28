package src.service.Category.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;
}