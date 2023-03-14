
package src.service.Orders.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class OrdersDto extends OrdersUpdateDto {
    @JsonProperty(value = "name", required = true)
    public UUID Id;
    @JsonProperty(value = "name", required = true)
    public Date createAt ;
    @JsonProperty(value = "name", required = true)
    public Date updateAt ;
}

