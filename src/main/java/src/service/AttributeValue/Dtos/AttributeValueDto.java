
package src.service.AttributeValue.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class AttributeValueDto extends AttributeValueUpdateDto {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "createAt", required = true)
    public Date createAt ;
    @JsonProperty(value = "updateAt", required = true)
    public Date updateAt ;
    @JsonProperty(value = "attributeId", required = true)
    public UUID attributeId;
    @JsonProperty(value = "orderItemId", required = true)
    public UUID orderItemId;
    @JsonProperty(value = "productId", required = true)
    public UUID productId;
}

