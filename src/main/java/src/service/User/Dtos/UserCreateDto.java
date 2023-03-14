package src.service.User.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserCreateDto {
    @JsonProperty(value = "firstName", required = true)
    private String firstName;
    @JsonProperty(value = "lastName", required = true)
    private String lastName;
    @JsonProperty(value = "middleName")
    private String middleName;
    @JsonProperty(value = "displayName", required = true, defaultValue = "0")
    private String displayName;
    @JsonProperty(value = "phoneNumber", required = true, defaultValue = "0")
    private String phoneNumber;
    @JsonProperty(value = "idCard", required = true)
    private String idCard;
    @JsonProperty(value = "email", required = true)
    private String email;
    @JsonProperty(value = "hashedPassword", required = true)
    private String hashedPassword;
    @JsonProperty(value = "avatar", required = true, defaultValue = "0")
    private String avatar;
    @JsonProperty(value = "userLevelId")
    private UUID userLevelId;
    @JsonProperty(value = "point", required = true, defaultValue = "0")
    private Double point;
    @JsonProperty(value = "eWallet", required = true, defaultValue = "0")
    private Double eWallet;
    @JsonProperty(value = "roleId")
    private UUID roleId;
}