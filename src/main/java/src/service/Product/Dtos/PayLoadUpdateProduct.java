package src.service.Product.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PayLoadUpdateProduct {
    @JsonProperty(value = "basic")
    private BasicDataProduct basic;
    @JsonProperty(value = "listAddAttribute")
    List<AttributeValueUpdate> listAddAttribute = new ArrayList<>();
    @JsonProperty(value = "listAddAttributeValue")
    List<AttributeValueUpdate> listAddAttributeValue = new ArrayList<>();
    @JsonProperty(value = "listRemoveAttributeValue")
    List<AttributeValueUpdate> listRemoveAttributeValue = new ArrayList<>();
    @JsonProperty(value = "listRemoveAttribute")
    List<AttributeValueUpdate> listRemoveAttribute = new ArrayList<>();
    @JsonProperty(value = "listEditAttribute")
    List<AttributeValueUpdate> listEditAttribute = new ArrayList<>();
    @JsonProperty(value = "listEditAttributeValue")
    List<AttributeValueUpdate> listEditAttributeValue = new ArrayList<>();
    @JsonProperty(value = "listAddImage")
    List<String> listAddImage = new ArrayList<>();
    @JsonProperty(value = "listRemoveImage")
    List<UUID> listRemoveImage = new ArrayList<>();
}
