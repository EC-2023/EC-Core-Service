package src.service.StoreLevel.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StoreLevelCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "minPoint", required = true, defaultValue = "0")
    public int minPoint ;
    @JsonProperty(value = "discount", required = true)
    public Double discount;
}