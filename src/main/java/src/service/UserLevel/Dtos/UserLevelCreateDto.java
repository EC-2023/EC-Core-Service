package src.service.UserLevel.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLevelCreateDto {
    @JsonProperty(value = "name", required = true)
    public String name;
    @JsonProperty(value = "minPoint", required = true, defaultValue = "0")
    public int minPoint ;
    @JsonProperty(value = "discount", required = true)
    public Double discount;
}