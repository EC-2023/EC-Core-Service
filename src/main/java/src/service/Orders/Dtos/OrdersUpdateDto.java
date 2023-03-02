
package src.service.Orders.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrdersUpdateDto extends  OrdersCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

