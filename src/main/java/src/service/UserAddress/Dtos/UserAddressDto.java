
package src.service.UserAddress.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class UserAddressDto extends UserAddressUpdateDto {
    @JsonProperty(value = "name", required = true)
    public UUID Id;
    @JsonProperty(value = "name", required = true)
    public Date createAt ;
    @JsonProperty(value = "name", required = true)
    public Date updateAt ;
}

