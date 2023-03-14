
package src.service.Delivery.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeliveryUpdateDto extends  DeliveryCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

