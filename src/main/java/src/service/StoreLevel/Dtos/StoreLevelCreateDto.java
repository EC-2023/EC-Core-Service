package src.service.StoreLevel.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StoreLevelCreateDto {
    @JsonProperty(value = "first_name", required = true)
    private String firstName;
}