
package src.service.UserLevel.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLevelUpdateDto extends  UserLevelCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted;
}

