package src.service.User.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import src.service.Role.Dtos.RoleDto;

import java.util.UUID;
@Data
public class UserProfileDto {
    @JsonProperty(value = "Id")
    public UUID Id;
    @JsonProperty(value = "email")
    public String email;
    @JsonProperty(value = "firstName")
    public String  firstName;
    @JsonProperty(value = "middleName")
    public String middleName;
    @JsonProperty(value = "lastName")
    public String lastName;
    @JsonProperty(value = "displayName")
    public String  displayName;
    @JsonProperty(value = "idCard")
    public String  idCard;
    @JsonProperty(value = "lastLogin")
    public String lastLogin;
    @JsonProperty(value = "avatar")
    public String  avatar;
    @JsonProperty(value = "point")
    public String  point;
    @JsonProperty(value = "phoneNumber")
    public String  phoneNumber;
    @JsonProperty(value = "eWallet")
    private Double eWallet;
    @JsonProperty(value = "role")
    private RoleDto roleByRoleId;
//    @JsonProperty(value = "roleId")
//    private UUID roleId;

}
