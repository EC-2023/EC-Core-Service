package src.service.OrderItems.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemsCreateDto {
    @JsonProperty(value = "productId", required = true)
    private UUID productId;
    @JsonProperty(value = "quantity", required = true)
    private int quantity;
    @JsonProperty(value = "orderId", required = true)
    private UUID orderId;
}