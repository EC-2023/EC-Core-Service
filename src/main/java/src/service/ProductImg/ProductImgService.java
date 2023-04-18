

package src.service.ProductImg;

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
import src.model.ProductImg;
import src.model.ProductImg;
import src.repository.IProductImgRepository;
import src.service.ProductImg.Dtos.ProductImgDto;
import src.service.ProductImg.Dtos.ProductImgCreateDto;
import src.service.ProductImg.Dtos.ProductImgDto;
import src.service.ProductImg.Dtos.ProductImgUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProductImgService implements IProductImgService {
    @Autowired
    private IProductImgRepository productimgRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<ProductImgDto>> getAll() {
        return CompletableFuture.completedFuture(
                productimgRepository.findAll().stream().map(
                        x -> toDto.map(x, ProductImgDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<ProductImgDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(productimgRepository.findById(id).get(), ProductImgDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<ProductImgDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = productimgRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<ProductImg> features = new ApiQuery<>(request, em, ProductImg.class, pagination);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, ProductImgDto.class)).toList()));
    }

    @Async
    public CompletableFuture<ProductImgDto> create(ProductImgCreateDto input) {
        ProductImg productimg = productimgRepository.save(toDto.map(input, ProductImg.class));
        return CompletableFuture.completedFuture(toDto.map(productimgRepository.save(productimg), ProductImgDto.class));
    }

    @Async
    public CompletableFuture<ProductImgDto> update(UUID id, ProductImgUpdateDto productimg) {
        ProductImg existingProductImg = productimgRepository.findById(id).orElse(null);
        if (existingProductImg == null)
            throw new NotFoundException("Unable to find product image!");
        BeanUtils.copyProperties(productimg, existingProductImg);
        existingProductImg.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(productimgRepository.save(existingProductImg), ProductImgDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        ProductImg existingProductImg = productimgRepository.findById(id).orElse(null);
        if (existingProductImg == null)
            throw new NotFoundException("Unable to find product image!");
        existingProductImg.setIsDeleted(true);
        existingProductImg.setUpdateAt(new Date(new java.util.Date().getTime()));
        productimgRepository.save(toDto.map(existingProductImg, ProductImg.class));
        return CompletableFuture.completedFuture(null);
    }
}

