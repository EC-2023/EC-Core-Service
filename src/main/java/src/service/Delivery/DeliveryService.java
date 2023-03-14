

package src.service.Delivery;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.Delivery;
import src.repository.IDeliveryRepository;
import src.service.Delivery.Dtos.DeliveryCreateDto;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.Delivery.Dtos.DeliveryUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class DeliveryService {
    @Autowired
    private IDeliveryRepository deliveryRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<DeliveryDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<DeliveryDto>) deliveryRepository.findAll().stream().map(
                        x -> toDto.map(x, DeliveryDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<DeliveryDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(deliveryRepository.findById(id), DeliveryDto.class));
    }

    @Async
    public CompletableFuture<DeliveryDto> create(DeliveryCreateDto input) {
        Delivery delivery = deliveryRepository.save(toDto.map(input, Delivery.class));
        return CompletableFuture.completedFuture(toDto.map(deliveryRepository.save(delivery), DeliveryDto.class));
    }

    @Async
    public CompletableFuture<DeliveryDto> update(UUID id, DeliveryUpdateDto delivery) {
        Delivery existingDelivery = deliveryRepository.findById(id).orElse(null);
        if (existingDelivery == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(deliveryRepository.save(toDto.map(delivery, Delivery.class)), DeliveryDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Delivery existingDelivery = deliveryRepository.findById(id).orElse(null);
        if (existingDelivery == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingDelivery.setIsDeleted(true);
        deliveryRepository.save(toDto.map(existingDelivery, Delivery.class));
        return CompletableFuture.completedFuture(null);
    }
}

