
package src.service.UserFollowProduct.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class UserFollowProductDto extends UserFollowProductUpdateDto {
 @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "createAt", required = true)
    public Date createAt ;
    @JsonProperty(value = "updateAt", required = true)
    public Date updateAt ;
}

