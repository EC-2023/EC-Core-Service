package src.service.ProductImg.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductImgCreateDto {
    @JsonProperty(value = "file name", required = true)
    private String fileName;
    @JsonProperty(value = "location", required = true)
    private String location;
    @JsonProperty(value = "product id", required = true)
    private UUID productId;
}