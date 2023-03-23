package src.service.CartItems.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CartItemsCreateDto {
    @JsonProperty(value = "cart id", required = true)
    private UUID cartID;
    @JsonProperty(value = "product id", required = true)
    private UUID productID;
    @JsonProperty(value = "quantity", required = true)
    private int quantity;
}