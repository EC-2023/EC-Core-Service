package src.service.Review.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewCreateDto {
    @JsonProperty(value = "content", required = true)
    private String content;
    @JsonProperty(value = "rating", required = true)
    private int rating;
    @JsonProperty(value = "storeId", required = true)
    private UUID storeId;

}