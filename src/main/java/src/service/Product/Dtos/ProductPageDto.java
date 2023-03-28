package src.service.Product.Dtos;

import java.util.List;

public class ProductPageDto {
    private List<ProductDto> products;
    private int pageNumber;
    private int totalPages;

    public ProductPageDto(List<ProductDto> products, int pageNumber, int totalPages) {
        this.products = products;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
    }

}
