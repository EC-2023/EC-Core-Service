package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;
}