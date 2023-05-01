
package src.service.Orders.Dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.OrderItems.Dtos.OrderItemsDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrdersDto extends OrdersUpdateDto {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "code", required = true)
    private String code;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted = false;
    @JsonProperty(value = "createAt", required = true)
    public Date createAt;
    @JsonProperty(value = "updateAt", required = true)
    public Date updateAt;
    @JsonProperty(value = "delivery")
    public DeliveryDto D;
    @JsonIgnore
    public double amountToGd;
    @JsonProperty(value = "orderItems")
    public List<OrderItemsDto> item = new ArrayList<>();
}

