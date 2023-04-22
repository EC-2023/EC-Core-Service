package src.service.Product.Dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AttributePayload {
    String name;
    List<String > values = new ArrayList<>();
}
