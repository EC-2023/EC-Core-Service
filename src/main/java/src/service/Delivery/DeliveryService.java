

package src.service.Delivery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.CartItems;
import src.model.Delivery;
import src.repository.IDeliveryRepository;
import src.service.Delivery.Dtos.DeliveryCreateDto;
import src.service.Delivery.Dtos.DeliveryDto;
import src.service.Delivery.Dtos.DeliveryUpdateDto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DeliveryService implements IDeliveryService {
    private final IDeliveryRepository deliveryRepository;
    private final ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    public DeliveryService(IDeliveryRepository deliveryRepository, ModelMapper toDto) {
        this.deliveryRepository = deliveryRepository;
        this.toDto = toDto;
    }

    @Async
    public CompletableFuture<List<DeliveryDto>> getAll() {
        return CompletableFuture.completedFuture(
                deliveryRepository.findAll().stream().map(
                        x -> toDto.map(x, DeliveryDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<DeliveryDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(deliveryRepository.findById(id).get(), DeliveryDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<DeliveryDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = deliveryRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<Delivery> features = new ApiQuery<>(request, em, Delivery.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
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

    @Override
    public long calcPrice(UUID deliveryId, String cityDest, List<CartItems> items) {
        long total = 0;
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new NotFoundException("Không tìm thấy phương thức vận chuyển"));
        HashMap<UUID, Long> count = new HashMap<>();
        for (CartItems item : items) {
            String src = item.getProductByProductId().getStoreByStoreId().getAddress();
            src = src.split(",")[src.split(",").length - 1].trim();
            Long currentPrice = count.get(item.getProductByProductId().getStoreId());
            if (currentPrice != null) {
                count.replace(item.getProductByProductId().getStoreId(), currentPrice + 2000);
                total += 2000;
            } else {
                long sum = 0;
                if ((cityDest.equals(("Hồ Chí Minh"))) && (src.equals(("Hồ Chí Minh"))) || (cityDest.equals(("Hà Nội"))) && (src.equals(("Hà Nội")))) {
                    sum += delivery.getPrice();
                } else {
                    sum += 10000;
                }
                total += sum;
                count.put(item.getProductByProductId().getStoreId(), sum);
            }
        }
        return total;

    }
}

