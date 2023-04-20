
package src.service.Category.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class CategoryDto extends CategoryUpdateDto {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "createAt")
    public Date createAt ;
    @JsonProperty(value = "updateAt")
    public Date updateAt ;
    @JsonProperty(value = "parentCategory")
    public ParentCategory parentCate ;
}

