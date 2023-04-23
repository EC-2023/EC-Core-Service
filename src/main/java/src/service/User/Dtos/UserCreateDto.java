package src.service.User.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserCreateDto {
    @JsonProperty(value = "firstName")
    private String firstName;
    @JsonProperty(value = "lastName")
    private String lastName;
    @JsonProperty(value = "middleName")
    private String middleName;
    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
    @JsonProperty(value = "idCard")
    private String idCard;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "hashedPassword")
    private String hashedPassword;
    @JsonProperty(value = "avatar")
    private String avatar;
}