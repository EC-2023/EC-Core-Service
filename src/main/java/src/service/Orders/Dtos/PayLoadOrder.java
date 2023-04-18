package src.service.Orders.Dtos;

import lombok.Data;
import src.model.CartItems;

import java.util.List;
import java.util.UUID;

@Data
public class PayLoadOrder {
    List<CartItems> orders;
    boolean isCOD;
    int option;
    UUID storeId;
    UUID deliveryId;
     String address;
     String phone;
}
