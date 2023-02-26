package src.config.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class Utils {
    @Autowired
    private ModelMapper modelMapper;
    public static <T, D> D toDto(T entity, Class<D> dtoClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, dtoClass);
    }
}
