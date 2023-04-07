package src.service.CartItems.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CartItemsCreateDto {
    @JsonProperty(value = "cartId", required = true)
    private UUID cartId;
    @JsonProperty(value = "productId", required = true)
    private UUID productId;
    @JsonProperty(value = "quantity", required = true)
    private int quantity;
}