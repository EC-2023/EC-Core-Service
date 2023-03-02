
package src.service.UserFollowProduct.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserFollowProductUpdateDto extends  UserFollowProductCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

