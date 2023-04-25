package src.service.User.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserCreateDto {
    @JsonProperty(value = "firstName")
    public String firstName;
    @JsonProperty(value = "lastName")
    public String lastName;
    @JsonProperty(value = "middleName")
    public String middleName;
    @JsonProperty(value = "phoneNumber")
    public String phoneNumber;

    @JsonProperty(value = "email")
    public String email;
    @JsonProperty(value = "hashedPassword")
    public String hashedPassword;
    @JsonProperty(value = "avatar")
    public String avatar;
}