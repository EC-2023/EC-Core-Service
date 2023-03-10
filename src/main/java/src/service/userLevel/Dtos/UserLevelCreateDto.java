package src.service.UserLevel.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLevelCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "min point", required = true, defaultValue = "0")
    private int minPoint;
    @JsonProperty(value = "discount", required = true, defaultValue = "0")
        private Double discount;
}