
package src.service.Attribute.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.service.AttributeValue.Dtos.AttributeValueDto;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
public class AttributeDto extends AttributeUpdateDto {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "createAt", required = true)
    public Date createAt ;
    @JsonProperty(value = "updateAt", required = true)
    public Date updateAt ;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;
    @JsonProperty(value = " attributeValues")
    public List<AttributeValueDto> attributeValueByAttribute;
}

