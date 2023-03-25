
package src.service.UserLevel.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLevelInputDto {
    @JsonProperty(value = "name", required = true)
    private String name;
}
