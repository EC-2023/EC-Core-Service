
package src.service.Category.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryUpdateDto extends  CategoryCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

