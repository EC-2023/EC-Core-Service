
package src.service.Commission.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommissionInputDto {
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "cost", required = true, defaultValue = "0")
    private Double cost;
    @JsonProperty(value = "description", required = true, defaultValue = "0")
    private String description;
}
