package src.service.UserAddress.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserAddressCreateDto {
    @JsonProperty(value = "city", required = true)
    public String city;
    @JsonProperty(value = "district", required = true)
    public String district;
    @JsonProperty(value = "nameRecipient", required = true)
    public String nameRecipient;
    @JsonProperty(value = "numberPhone", required = true)
    public String numberPhone;
    @JsonProperty(value = "ward", required = true)
    public String ward;
    @JsonProperty(value = "detailAddress", required = true)
    private String detailAddress;
    @JsonProperty(value = "zipcode")
    public String zipcode;
}