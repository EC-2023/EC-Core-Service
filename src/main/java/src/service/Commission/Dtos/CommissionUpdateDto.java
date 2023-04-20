
package src.service.Commission.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommissionUpdateDto extends  CommissionCreateDto{
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted;
}

