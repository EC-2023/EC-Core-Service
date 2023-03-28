
package src.service.Product;

import src.service.IService;
import src.service.Product.Dtos.ProductCreateDto;
import src.service.Product.Dtos.ProductDto;
import src.service.Product.Dtos.ProductUpdateDto;

public interface IProductService extends IService<ProductDto, ProductCreateDto, ProductUpdateDto> {
}
