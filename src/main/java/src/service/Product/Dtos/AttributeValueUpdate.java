package src.service.Product.Dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AttributeValueUpdate {
    String name;
    UUID id;
    boolean isDeleted;

}
