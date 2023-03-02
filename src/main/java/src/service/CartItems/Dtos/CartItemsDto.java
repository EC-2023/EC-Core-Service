
package src.service.CartItems.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class CartItemsDto extends CartItemsUpdateDto {
    @JsonProperty(value = "name", required = true)
    public UUID Id;
    @JsonProperty(value = "name", required = true)
    public Date createAt ;
    @JsonProperty(value = "name", required = true)
    public Date updateAt ;
}

