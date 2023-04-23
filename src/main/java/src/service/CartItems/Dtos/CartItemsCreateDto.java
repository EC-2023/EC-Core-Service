package src.service.CartItems.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartItemsCreateDto {
    @JsonProperty(value = "productId")
    private UUID productId;
    @JsonProperty(value = "quantity")
    private int quantity;
    @JsonProperty(value = "quantity")
    private List<String> attributesValues = new ArrayList<>();
}