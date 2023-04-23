package src.service.Store.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CategoryProductDto {
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "Id")
    UUID Id;
    @JsonProperty(value = "createAt")
    public Date createAt ;
    @JsonProperty(value = "updateAt")
    public Date updateAt ;
}
