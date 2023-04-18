
package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductUpdateDto extends  ProductCreateDto{
    @JsonProperty(value = "sold", required = true)
    private int sold;

}

