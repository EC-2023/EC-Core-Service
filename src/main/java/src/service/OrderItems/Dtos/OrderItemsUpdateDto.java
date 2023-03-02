
package src.service.OrderItems.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemsUpdateDto extends  OrderItemsCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

