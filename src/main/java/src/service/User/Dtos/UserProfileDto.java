package src.service.User.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserProfileDto {
    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }


    @JsonProperty(value = "Id", required = true)
    public UUID Id;

    @JsonProperty(value = "email", required = true)
    public String email;

    @JsonProperty(value = "firstName")
    public String  firstName;

    @JsonProperty(value = "middleName")
    public String middleName;

    @JsonProperty(value = "lastName")
    public String lastName;

    @JsonProperty(value = "displayName")
    public String  displayName;

    @JsonProperty(value = "lastLogin")
    public String lastLogin;
}
