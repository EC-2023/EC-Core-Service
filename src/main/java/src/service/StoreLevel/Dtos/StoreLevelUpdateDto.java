
package src.service.StoreLevel.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StoreLevelUpdateDto extends StoreLevelCreateDto {
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted;
}

