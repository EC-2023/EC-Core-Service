package src.service.Category.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "parentCategoryId", required = true)
    public UUID parentCategoryId;
}