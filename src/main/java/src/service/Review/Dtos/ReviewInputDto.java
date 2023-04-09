
package src.service.Review.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReviewInputDto {
    @JsonProperty(value = "content", required = true)
    private String content;
    @JsonProperty(value = "rating", required = true)
    private int rating;
}
