
package src.service.UserFollowStore.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UserFollowStoreDto extends UserFollowStoreUpdateDto {
   @JsonProperty(value = "Id", required = true)
   public UUID Id;
   @JsonProperty(value = "createAt", required = true)
   public Date createAt ;
   @JsonProperty(value = "updateAt", required = true)
   public Date updateAt ;
   @JsonProperty(value = "isDeleted")
   public Boolean isDeleted  = false;
   @JsonProperty(value = "userId", required = true)
   public UUID userId;

}

