
package src.service.Commission.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class CommissionDto extends CommissionUpdateDto {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;
    @JsonProperty(value = "createAt", required = true)
    public Date createAt ;
    @JsonProperty(value = "updateAt", required = true)
    public Date updateAt ;
}

