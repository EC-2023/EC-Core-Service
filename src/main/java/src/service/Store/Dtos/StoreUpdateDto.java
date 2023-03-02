
package src.service.Store.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StoreUpdateDto extends  StoreCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;

}

