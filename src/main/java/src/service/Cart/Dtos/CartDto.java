
package src.service.Cart.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.service.CartItems.Dtos.CartItemsDto;
import src.service.Store.Dtos.StoreProductDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto extends CartUpdateDto {
   @JsonProperty(value = "Id")
   public UUID Id;
   @JsonProperty(value="userId")
   private UUID userId;
   @JsonProperty(value = "isDeleted")
   public Boolean isDeleted  = false;
   @JsonProperty(value = "createAt")
   public Date createAt ;
   @JsonProperty(value = "updateAt")
   public Date updateAt ;
   @JsonProperty(value = "cartItems")
   public List<CartItemsDto> cartItemsByCartId ;
   @JsonProperty(value = "store")
   public StoreProductDto storeByStoreId;
}

