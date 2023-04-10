

package src.service.Orders;

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
import src.model.Orders;
import src.model.Orders;
import src.repository.IOrdersRepository;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersCreateDto;
import src.service.Orders.Dtos.OrdersDto;
import src.service.Orders.Dtos.OrdersUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OrdersService implements IOrdersService {
    @Autowired
    private IOrdersRepository ordersRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<OrdersDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<OrdersDto>) ordersRepository.findAll().stream().map(
                        x -> toDto.map(x, OrdersDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<OrdersDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.findById(id).get(), OrdersDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<OrdersDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        ApiQuery<Orders> features = new ApiQuery<>(request, em, Orders.class);
        long total = ordersRepository.count();
        return CompletableFuture.completedFuture(PagedResultDto.create(Pagination.create(total, skip, limit),
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, OrdersDto.class)).toList()));
    }

    @Async
    public CompletableFuture<OrdersDto> create(OrdersCreateDto input) {
        Orders orders = ordersRepository.save(toDto.map(input, Orders.class));
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(orders), OrdersDto.class));
    }

    @Async
    public CompletableFuture<OrdersDto> update(UUID id, OrdersUpdateDto orders) {
        Orders existingOrders = ordersRepository.findById(id).orElse(null);
        if (existingOrders == null)
            throw new NotFoundException("Unable to find orders!");
        BeanUtils.copyProperties(orders, existingOrders);
        existingOrders.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(ordersRepository.save(existingOrders), OrdersDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Orders existingOrders = ordersRepository.findById(id).orElse(null);
        if (existingOrders == null)
            throw new NotFoundException("Unable to find orders!");
        existingOrders.setIsDeleted(true);
        existingOrders.setUpdateAt(new Date(new java.util.Date().getTime()));
        ordersRepository.save(toDto.map(existingOrders, Orders.class));
        return CompletableFuture.completedFuture(null);
    }
}

