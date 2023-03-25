package src.service.Orders.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class OrdersCreateDto {
    @JsonProperty(value = "address", required = true)
    private String address;
    @JsonProperty(value = "amountFromUser", required = true)
    private double amountFromUser;
    @JsonProperty(value = "amountToGd")
    private double amountToGd;
    @JsonProperty(value = "amountToStore", required = true)
    private double amountToStore;
    @JsonProperty(value = "deliveryId", required = true)
    private UUID deliveryId;
    @JsonProperty(value = "isPaidBefore", required = true)
    private boolean isPaidBefore;
    @JsonProperty(value = "phone", required = true)
    private String phone;
    @JsonProperty(value = "status", required = true)
    private String status;
    @JsonProperty(value = "storeId", required = true)
    private UUID storeId;
    @JsonProperty(value = "userId", required = true)
    private UUID userId;
}