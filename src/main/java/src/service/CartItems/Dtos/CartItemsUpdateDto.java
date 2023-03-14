
package src.service.CartItems.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartItemsUpdateDto extends  CartItemsCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

