
package src.service.Delivery.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class DeliveryDto extends DeliveryUpdateDto {

   @JsonProperty(value = "Id", required = true)
   public UUID Id;
   @JsonProperty(value = "createAt", required = true)
   public Date createAt ;
   @JsonProperty(value = "updateAt", required = true)
   public Date updateAt ;
   @JsonProperty(value = "isDeleted")
   public Boolean isDeleted  = false;

}

