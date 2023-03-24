

package src.service.ProductImg;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.config.exception.NotFoundException;
import src.model.ProductImg;
import src.repository.IProductImgRepository;
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
public class ProductImgService {
    @Autowired
    private IProductImgRepository productimgRepository;
    @Autowired
    private ModelMapper toDto;

    @Async
    public CompletableFuture<List<ProductImgDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<ProductImgDto>) productimgRepository.findAll().stream().map(
                        x -> toDto.map(x, ProductImgDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<ProductImgDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(productimgRepository.findById(id).get(), ProductImgDto.class));
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
        Date createAt = existingProductImg.getCreateAt();
        existingProductImg = toDto.map(productimg, ProductImg.class);
        existingProductImg.setId(id);
        existingProductImg.setCreateAt(createAt);
        return CompletableFuture.completedFuture(toDto.map(productimgRepository.save(existingProductImg), ProductImgDto.class));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        ProductImg existingProductImg = productimgRepository.findById(id).orElse(null);
        if (existingProductImg == null)
            throw new NotFoundException("Unable to find product image!");
        existingProductImg.setIsDeleted(true);
        productimgRepository.save(toDto.map(existingProductImg, ProductImg.class));
        return CompletableFuture.completedFuture(null);
    }
}

