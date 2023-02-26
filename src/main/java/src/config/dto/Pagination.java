package src.config.dto;

import src.config.dto.payload.PaginationPayload;

public class Pagination<T> {

    private int total;
    private int skip;
    private int limit;

    public Pagination(int total, int skip, int limit) {
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public static Pagination create(PaginationPayload payload) {
        return new Pagination(payload.skip, payload.limit, payload.total);
    }


}


