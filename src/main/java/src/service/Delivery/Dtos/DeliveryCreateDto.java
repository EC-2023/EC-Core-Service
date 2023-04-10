package src.service.Delivery.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class DeliveryCreateDto {

    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "price", required = true)
    private double price;
    @JsonProperty(value = "description", required = true)
    private String description;

}