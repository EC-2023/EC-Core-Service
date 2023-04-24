package src.service.Store.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class StoreProductDto
{
    @JsonProperty(value = "Id")
    UUID Id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "createAt")
    public Date createAt ;
    @JsonProperty(value = "updateAt")
    public Date updateAt ;
    @JsonProperty(value = "totalProduct")
    private int totalProduct;
    @JsonProperty(value = "totalUserFollow")
    private int totalUserFollow;
    @JsonProperty(value = "rating")
    private int rating = 0;
}
