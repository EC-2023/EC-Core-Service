
package src.service.Review.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ReviewDto extends ReviewUpdateDto {
    @JsonProperty(value = "Id")
    public UUID Id;
    @JsonProperty(value = "createAt")
    public Date createAt;
    @JsonProperty(value = "updateAt")
    public Date updateAt;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted;
    @JsonProperty(value = "user")
    public UserDto userByUserId;
}

