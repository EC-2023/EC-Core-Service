
package src.service.OrderItems.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.model.AttributeValue;
import src.model.Product;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
public class OrderItemsDto extends OrderItemsUpdateDto {
   @JsonProperty(value = "Id", required = true)
   public UUID Id;
   @JsonProperty(value = "createAt", required = true)
   public Date createAt ;
   @JsonProperty(value = "updateAt", required = true)
   public Date updateAt ;
   @JsonProperty(value = "isDeleted")
   public Boolean isDeleted  = false;
   @JsonProperty(value = "product")
   public Product productByProductId;
   @JsonProperty(value = "attributeValues")
   public Collection<AttributeValue> attributesValueByOrderItemId;
}

