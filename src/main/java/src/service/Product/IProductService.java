
package src.service.Product;

import src.service.IService;
import src.service.Product.Dtos.ProductCreateDto;
import src.service.Product.Dtos.ProductDto;
import src.service.Product.Dtos.ProductUpdateDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"ALL", "CommentedOutCode"})
public interface IProductService extends IService<ProductDto, ProductCreateDto, ProductUpdateDto> {
//
//    public CompletableFuture<Void> getLatestProduct(HttpServletRequest request);
//
//    public CompletableFuture<Void> getSaleProduct(HttpServletRequest request);
//
//    public CompletableFuture<Void> getBestSellerProduct(HttpServletRequest request);

    public CompletableFuture<ProductDto> updateQuantity(UUID userId, UUID productId, int quantity);
    public CompletableFuture<ProductDto> updateStatusProduct(UUID userId, UUID productId, boolean status);
    public CompletableFuture<ProductDto> updateActiveProduct(UUID userId, UUID productId, boolean status);
}
