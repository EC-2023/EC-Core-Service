
package src.service.Cart.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CartDto extends CartUpdateDto {
   @JsonProperty(value = "Id", required = true)
   public UUID Id;
   @JsonProperty(value="userId", required = true)
   private UUID userId;
   @JsonProperty(value = "isDeleted")
   public Boolean isDeleted  = false;
   @JsonProperty(value = "createAt", required = true)
   public Date createAt ;
   @JsonProperty(value = "updateAt", required = true)
   public Date updateAt ;
}

