package src.service.UserAddress.Dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAddressCreateDto {
    @JsonProperty(value = "city", required = true)
    private String city;

    @JsonProperty(value = "country", required = true, defaultValue = "0")
    private String country;
    @JsonProperty(value = "district", required = true, defaultValue = "0")
    private String district;

    @JsonProperty(value = "ameRecipient", required = true, defaultValue = "0")
    private String nameRecipient;
    @JsonProperty(value = "numberPhone", required = true, defaultValue = "0")
    private String numberPhone;

    @JsonProperty(value = "ward", required = true, defaultValue = "0")
    private String ward;
    @JsonProperty(value = "zipcode", required = true, defaultValue = "0")
    private String zipcode;

}