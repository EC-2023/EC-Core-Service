package src.service.StoreLevel.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StoreLevelCreateDto {
    @JsonProperty(value = "name", required = true)
    private String name;
}