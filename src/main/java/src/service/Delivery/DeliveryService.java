

package src.service.Delivery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.Delivery;
import src.repository.IDeliveryRepository;
import src.service.Delivery.Dtos.DeliveryCreateDto;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.Delivery.Dtos.DeliveryUpdateDto;

import java.util.Date;
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

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<DeliveryDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<DeliveryDto>) deliveryRepository.findAll().stream().map(
                        x -> toDto.map(x, DeliveryDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<DeliveryDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(deliveryRepository.findById(id).get(), DeliveryDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<DeliveryDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        ApiQuery<Delivery> features = new ApiQuery<>(request, em, Delivery.class);
        long total = deliveryRepository.count();
        return CompletableFuture.completedFuture(PagedResultDto.create(Pagination.create(total, skip, limit),
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, DeliveryDto.class)).toList()));
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
            throw new NotFoundException("Unable to find delivery!");
        BeanUtils.copyProperties(delivery, existingDelivery);
        existingDelivery.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(deliveryRepository.save(existingDelivery), DeliveryDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Delivery existingDelivery = deliveryRepository.findById(id).orElse(null);
        if (existingDelivery == null)
            throw new NotFoundException("Unable to find delivery!");
        existingDelivery.setIsDeleted(true);
        existingDelivery.setUpdateAt(new Date(new java.util.Date().getTime()));
        deliveryRepository.save(existingDelivery);
        return CompletableFuture.completedFuture(null);
    }
}

