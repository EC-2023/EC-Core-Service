
package src.service.UserAddress.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAddressUpdateDto extends  UserAddressCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}
