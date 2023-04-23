package src.service.Statistic.Dtos;

import lombok.Data;

@Data
public class PayLoadTotalStatistic {
    private long totalProduct;
    private long totalStore;
    private long totalUser;

    public static PayLoadTotalStatistic create(long totalProduct, long totalStore, long totalUser) {
        return new PayLoadTotalStatistic(totalProduct, totalStore, totalUser);
    }

    public PayLoadTotalStatistic(long totalProduct, long totalStore, long totalUser) {
        this.totalProduct = totalProduct;
        this.totalStore = totalStore;
        this.totalUser = totalUser;
    }


}
