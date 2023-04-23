
package src.service.User.Dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import src.service.Role.Dtos.RoleDto;

import java.util.Date;
import java.util.UUID;

@Data
public class UserDto extends UserUpdateDto {
    @JsonProperty(value = "Id", required = true)
    public UUID Id;
    @JsonProperty(value = "createAt", required = true)
    public Date createAt ;
    @JsonProperty(value = "updateAt", required = true)
    public Date updateAt ;
    @JsonProperty(value = "roleByRoleId")
    @JsonSerialize
    private RoleDto roleByRoleId;
    @JsonProperty(value = "isDeleted")
    public Boolean isDeleted  = false;
    @Override
    public String getHashedPassword(){
        return null;
    }
    @JsonProperty(value = "displayName", required = true, defaultValue = "0")
    private String displayName;
    @JsonProperty(value = "userLevelId")
    private UUID userLevelId;
    @JsonProperty(value = "eWallet", required = true, defaultValue = "0")
    private Double eWallet;
    @JsonProperty(value = "point")
    public String  point;

//    @Override
//    public String getIdCard(){
//        return null;
//    }
}

