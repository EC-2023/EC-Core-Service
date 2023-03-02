
package src.service.Role.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoleUpdateDto extends  RoleCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

