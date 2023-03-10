package src.service.Role.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;

}