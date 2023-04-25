package src.service.Product.Dtos;

import lombok.Data;

import java.util.UUID;
@Data
public class AttributeUpdate {
    String name;
    UUID id;
    Boolean isDeleted;
}
