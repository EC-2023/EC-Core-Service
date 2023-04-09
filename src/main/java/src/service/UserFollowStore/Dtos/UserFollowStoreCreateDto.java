package src.service.UserFollowStore.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserFollowStoreCreateDto {
    @JsonProperty(value = "storeId", required = true)
    public UUID storeId;


}