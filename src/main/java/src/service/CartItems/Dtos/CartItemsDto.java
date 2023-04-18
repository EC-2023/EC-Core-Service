
package src.service.CartItems.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.service.Product.Dtos.ProductDto;

import java.util.Date;
import java.util.UUID;

@Data
public class CartItemsDto extends CartItemsUpdateDto {
   @JsonProperty(value = "Id", required = true)
   public UUID Id;
   @JsonProperty(value = "isDeleted")
   public Boolean isDeleted  = false;
   @JsonProperty(value = "createAt", required = true)
   public Date createAt ;
   @JsonProperty(value = "updateAt", required = true)
   public Date updateAt ;
   @JsonProperty(value = "product")
   private ProductDto productByProductId;
}

