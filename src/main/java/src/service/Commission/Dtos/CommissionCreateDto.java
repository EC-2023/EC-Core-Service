package src.service.Commission.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommissionCreateDto {
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "cost", defaultValue = "0")
    private Double cost;
    @JsonProperty(value = "description", defaultValue = "0")
    private String description;
}