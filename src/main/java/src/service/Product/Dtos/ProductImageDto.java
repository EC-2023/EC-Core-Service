package src.service.Product.Dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductImageDto {
    private UUID Id;
    private UUID productId;
    private String fileName;
    private String location;
}
