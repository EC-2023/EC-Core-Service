
package src.service.Attribute.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttributeUpdateDto extends  AttributeCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

