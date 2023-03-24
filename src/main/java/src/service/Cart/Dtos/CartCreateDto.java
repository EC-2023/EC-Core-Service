package src.service.Cart.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CartCreateDto {
    @JsonProperty(value="userId", required = true)
    private UUID userId;
}