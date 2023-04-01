
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
    @Override
    public String getHashedPassword(){
        return null;
    }

    @Override
    public String getIdCard(){
        return null;
    }
}

