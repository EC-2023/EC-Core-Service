package src.config.dto;

import src.config.dto.payload.PagedResultDtoPayload;

import java.util.List;

public class PagedResultDto<TDto> {
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    private Pagination pagination;
    private List<TDto> data;

    public PagedResultDto(Pagination pagination, List<TDto> data) {
        this.data = data;
        this.pagination = pagination;
    }

    public static <TDto>PagedResultDto<TDto> create(PagedResultDtoPayload<TDto> payload) {
        return new PagedResultDto(payload.getPagination(), payload.getData());
    }
}
