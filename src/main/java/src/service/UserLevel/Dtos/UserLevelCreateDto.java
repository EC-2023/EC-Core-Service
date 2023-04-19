package src.service.UserLevel.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLevelCreateDto {
    @JsonProperty(value = "name")
    public String name;
    @JsonProperty(value = "minPoint")
    public int minPoint ;
    @JsonProperty(value = "discount")
    public Double discount;
}