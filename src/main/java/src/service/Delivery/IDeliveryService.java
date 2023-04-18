
package src.service.Delivery;

import src.model.CartItems;
import src.service.Delivery.Dtos.DeliveryCreateDto;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.Delivery.Dtos.DeliveryUpdateDto;
import src.service.IService;

import java.util.List;
import java.util.UUID;

public interface IDeliveryService extends IService<DeliveryDto, DeliveryCreateDto, DeliveryUpdateDto> {
    public long calcPrice(UUID deliveryId, String cityDest, List<CartItems> items);
}
