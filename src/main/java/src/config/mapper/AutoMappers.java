package src.config.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import src.config.dto.Pagination;
import src.config.dto.PaginationSerializer;

public  class AutoMappers {
    public AutoMappers() {
        // map paginate

    }
    public static void create(){
        SimpleModule module = new SimpleModule();
        module.addSerializer(Pagination.class, new PaginationSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }
}
