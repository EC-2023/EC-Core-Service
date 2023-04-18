
package src.service.StoreLevel.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class StoreLevelDto extends StoreLevelUpdateDto {
   @JsonProperty(value = "Id", required = true)
   public UUID Id;
   @JsonProperty(value = "isDeleted")
   public Boolean isDeleted  = false;
   @JsonProperty(value = "createAt", required = true)
   public Date createAt ;
   @JsonProperty(value = "updateAt", required = true)
   public Date updateAt ;
}

