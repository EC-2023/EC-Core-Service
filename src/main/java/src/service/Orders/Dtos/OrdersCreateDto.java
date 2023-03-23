package src.service.Orders.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class OrdersCreateDto {
    @JsonProperty(value = "address", required = true)
    private String address;
    @JsonProperty(value = "amount from user", required = true)
    private double amountFromUser;
    @JsonProperty(value = "amount togd")
    private double amountToGd;
    @JsonProperty(value = "amount to store", required = true)
    private double amountToStore;
    @JsonProperty(value = "delivery id", required = true)
    private UUID deliveryId;
    @JsonProperty(value = "is paid before", required = true)
    private boolean isPaidBefore;
    @JsonProperty(value = "phone", required = true)
    private String phone;
    @JsonProperty(value = "status", required = true)
    private String status;
    @JsonProperty(value = "store id", required = true)
    private UUID storeId;
    @JsonProperty(value = "user id", required = true)
    private UUID userId;
}