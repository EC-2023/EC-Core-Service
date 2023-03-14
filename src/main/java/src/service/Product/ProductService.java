

package src.service.Product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.model.Product;
import src.repository.IProductRepository;
import src.service.Product.Dtos.ProductCreateDto;
import src.service.Product.Dtos.ProductDto;
import src.service.Product.Dtos.ProductUpdateDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<ProductDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<ProductDto>) productRepository.findAll().stream().map(
                        x -> toDto.map(x, ProductDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<ProductDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(productRepository.findById(id), ProductDto.class));
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
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(productRepository.save(toDto.map(product, Product.class)), ProductDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        existingProduct.setDeleted(true);
        productRepository.save(toDto.map(existingProduct, Product.class));
        return CompletableFuture.completedFuture(null);
    }
}

