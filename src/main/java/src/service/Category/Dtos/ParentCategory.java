package src.service.Category.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class ParentCategory {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "createAt")
    public Date createAt ;
    @JsonProperty(value = "updateAt")
    public Date updateAt ;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "parentCategoryId")
    public UUID parentCategoryId;
    @JsonProperty(value = "image")
    public String image;
}
