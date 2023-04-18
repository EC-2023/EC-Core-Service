

package src.service.OrderItems;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.OrderItems;
import src.model.Product;
import src.repository.IOrderItemsRepository;
import src.service.OrderItems.Dtos.OrderItemsCreateDto;
import src.service.OrderItems.Dtos.OrderItemsDto;
import src.service.OrderItems.Dtos.OrderItemsUpdateDto;
import src.service.Product.Dtos.ProductDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OrderItemsService implements IOrderItemsService {
    @Autowired
    private IOrderItemsRepository orderitemsRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<OrderItemsDto>> getAll() {
        return CompletableFuture.completedFuture(
                orderitemsRepository.findAll().stream().map(
                        x -> toDto.map(x, OrderItemsDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<OrderItemsDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(orderitemsRepository.findById(id).get(), OrderItemsDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<OrderItemsDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = orderitemsRepository.count();
              Pagination pagination = Pagination.create(total, skip, limit);

        ApiQuery<OrderItems> features = new ApiQuery<>(request, em, OrderItems.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, OrderItemsDto.class)).toList()));
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
            throw new NotFoundException("Unable to find Order Items!");
        BeanUtils.copyProperties(orderitems, existingOrderItems);
        existingOrderItems.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(orderitemsRepository.save(existingOrderItems), OrderItemsDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        OrderItems existingOrderItems = orderitemsRepository.findById(id).orElse(null);
        if (existingOrderItems == null)
            throw new NotFoundException("Unable to find Order Items!");
        existingOrderItems.setIsDeleted(true);
        existingOrderItems.setUpdateAt(new Date(new java.util.Date().getTime()));
        orderitemsRepository.save(toDto.map(existingOrderItems, OrderItems.class));
        return CompletableFuture.completedFuture(null);
    }
}

