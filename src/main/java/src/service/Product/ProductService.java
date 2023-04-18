

package src.service.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.model.Product;
import src.repository.IProductRepository;
import src.service.Product.Dtos.ProductCreateDto;
import src.service.Product.Dtos.ProductDto;
import src.service.Product.Dtos.ProductUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ModelMapper toDto;

    @PersistenceContext
    EntityManager em;

    @Async
    public CompletableFuture<List<ProductDto>> getAll() {
        return CompletableFuture.completedFuture(
                productRepository.findAll().stream().map(
                        x -> toDto.map(x, ProductDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<ProductDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(productRepository.findById(id).get(), ProductDto.class));
    }

    @Async
    public CompletableFuture<PagedResultDto<ProductDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        long total = productRepository.count();
        Pagination pagination = Pagination.create(total, skip, limit);
        ApiQuery<Product> features = new ApiQuery<>(request, em, Product.class, pagination);
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, ProductDto.class)).toList()));
    }

    @Async
    public CompletableFuture<ProductDto> create(ProductCreateDto input) {
        Product product = productRepository.save(toDto.map(input, Product.class));
        return CompletableFuture.completedFuture(toDto.map(productRepository.save(product), ProductDto.class));
    }

    @Async
    public CompletableFuture<ProductDto> update(UUID id, ProductUpdateDto product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null)
            throw new NotFoundException("Unable to find product!");
        BeanUtils.copyProperties(product, existingProduct);
        existingProduct.setUpdateAt(new Date(new java.util.Date().getTime()));
        return CompletableFuture.completedFuture(toDto.map(productRepository.save(existingProduct), ProductDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null)
            throw new NotFoundException("Unable to find product!");
        existingProduct.setIsDeleted(true);
        existingProduct.setUpdateAt(new Date(new java.util.Date().getTime()));
        productRepository.save(toDto.map(existingProduct, Product.class));
        return CompletableFuture.completedFuture(null);
    }


}

