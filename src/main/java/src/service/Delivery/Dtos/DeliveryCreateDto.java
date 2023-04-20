package src.service.Delivery.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeliveryCreateDto {
    @JsonProperty(value = "name", required = true)
    public String name;
    @JsonProperty(value = "price", required = true)
    public double price;
    @JsonProperty(value = "description", required = true)
    public String description;

    
}