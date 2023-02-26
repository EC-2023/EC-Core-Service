package src.config.dto.payload;

import src.config.dto.Pagination;

import java.util.List;

public class PagedResultDtoPayload<TDto> {
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<TDto> getData() {
        return data;
    }

    public void setData(List<TDto> data) {
        this.data = data;
    }

    Pagination pagination ;
    List<TDto> data;
}
