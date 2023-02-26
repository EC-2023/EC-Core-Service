package src.config.dto;

public class Pagination {

    private int total;
    private int skip;
    private int limit;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Pagination(int total, int skip, int limit) {
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public static Pagination create(int total, int skip, int limit) {
        return new Pagination(skip, limit, total);
    }

}


