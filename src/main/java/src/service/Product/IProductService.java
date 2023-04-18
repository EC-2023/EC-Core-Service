
package src.service.Product;

import src.service.IService;
import src.service.Product.Dtos.ProductCreateDto;
import src.service.Product.Dtos.ProductDto;
import src.service.Product.Dtos.ProductUpdateDto;

@SuppressWarnings({"ALL", "CommentedOutCode"})
public interface IProductService extends IService<ProductDto, ProductCreateDto, ProductUpdateDto> {
//
//    public CompletableFuture<Void> getLatestProduct(HttpServletRequest request);
//
//    public CompletableFuture<Void> getSaleProduct(HttpServletRequest request);
//
//    public CompletableFuture<Void> getBestSellerProduct(HttpServletRequest request);
}
