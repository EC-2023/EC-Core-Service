
package src.service.Store.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class StoreDto extends StoreUpdateDto {
 @JsonProperty(value = "Id")
    public UUID Id;
    @JsonProperty(value = "createAt")
    public Date createAt ;
    @JsonProperty(value = "updateAt")
    public Date updateAt ;
   @JsonProperty(value = "point")
   private int point = 0;
   @JsonProperty(value = "rating")
   private int rating = 0;
   @JsonProperty(value = "e_wallet")
   private int e_wallet = 0;
   @JsonProperty(value = "isActive")
   private boolean isActive;
   @JsonProperty(value = "commissionId")
   private UUID commissionId;
   @JsonProperty(value = "ownId")
   private UUID ownId;
   @JsonProperty(value = "totalProduct")
   private int totalProduct;
   @JsonProperty(value = "totalUserFollow")
   private int totalUserFollow;
    //   @JsonProperty(value = "userByOwnId")
    //   private UserDto userByOwnId;
}

