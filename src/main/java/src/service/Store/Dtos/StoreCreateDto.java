package src.service.Store.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StoreCreateDto {
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "bio")
    private String bio;
    @JsonProperty(value = "address")
    private String address;
    @JsonProperty(value = "avatar")
    private String avatar;
    @JsonProperty(value = "cover")
    private String cover;
    @JsonProperty(value = "featuredImages")
    private String featuredImages;
}