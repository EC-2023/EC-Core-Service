package src.service.UserFollowProduct.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserFollowProductCreateDto {

    @JsonProperty(value = "productId", required = true)
    public UUID productId;


}