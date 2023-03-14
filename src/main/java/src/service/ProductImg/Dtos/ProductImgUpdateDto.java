
package src.service.ProductImg.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductImgUpdateDto extends  ProductImgCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

