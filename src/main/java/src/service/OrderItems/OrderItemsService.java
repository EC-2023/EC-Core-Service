

package src.service.OrderItems;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.OrderItems;
import src.repository.IOrderItemsRepository;
import src.service.OrderItems.Dtos.OrderItemsCreateDto;
import src.service.OrderItems.Dtos.OrderItemsDto;
import src.service.OrderItems.Dtos.OrderItemsUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OrderItemsService {
    @Autowired
    private IOrderItemsRepository orderitemsRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<OrderItemsDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<OrderItemsDto>) orderitemsRepository.findAll().stream().map(
                        x -> toDto.map(x, OrderItemsDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<OrderItemsDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(orderitemsRepository.findById(id), OrderItemsDto.class));
    }

    @Async
    public CompletableFuture<OrderItemsDto> create(OrderItemsCreateDto input) {
        OrderItems orderitems = orderitemsRepository.save(toDto.map(input, OrderItems.class));
        return CompletableFuture.completedFuture(toDto.map(orderitemsRepository.save(orderitems), OrderItemsDto.class));
    }

    @Async
    public CompletableFuture<OrderItemsDto> update(UUID id, OrderItemsUpdateDto orderitems) {
        OrderItems existingOrderItems = orderitemsRepository.findById(id).orElse(null);
        if (existingOrderItems == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(orderitemsRepository.save(toDto.map(orderitems, OrderItems.class)), OrderItemsDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        OrderItems existingOrderItems = orderitemsRepository.findById(id).orElse(null);
        if (existingOrderItems == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingOrderItems.setIsDeleted(true);
        orderitemsRepository.save(toDto.map(existingOrderItems, OrderItems.class));
        return CompletableFuture.completedFuture(null);
    }
}

