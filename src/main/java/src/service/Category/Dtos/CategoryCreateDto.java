package src.service.Category.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryCreateDto {
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "parentCategoryId")
    public UUID parentCategoryId;
    @JsonProperty(value = "image")
    public String image;
}