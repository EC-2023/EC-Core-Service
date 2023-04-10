package src.service.UserAddress.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserAddressCreateDto {
    @JsonProperty(value = "city", required = true)
    public String city;
    @JsonProperty(value = "country", required = true, defaultValue = "0")
    public String country;
    @JsonProperty(value = "district", required = true, defaultValue = "0")
    public String district;
    @JsonProperty(value = "nameRecipient", required = true, defaultValue = "0")
    public String nameRecipient;
    @JsonProperty(value = "numberPhone", required = true, defaultValue = "0")
    public String numberPhone;
    @JsonProperty(value = "ward", required = true, defaultValue = "0")
    public String ward;
    @JsonProperty(value = "zipcode", required = true, defaultValue = "0")
    public String zipcode;


}