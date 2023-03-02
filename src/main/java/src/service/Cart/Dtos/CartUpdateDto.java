
package src.service.Cart.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartUpdateDto extends  CartCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

