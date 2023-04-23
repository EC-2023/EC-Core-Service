package src.service.Statistic.Dtos;

import lombok.Data;

@Data
public class PayLoadTotalStore {
    long totalProduct;
    double totalRevenue;

    public PayLoadTotalStore(long totalProduct, double totalRevenue) {
        this.totalProduct = totalProduct;
        this.totalRevenue = totalRevenue;
    }

    public static PayLoadTotalStore create(long totalProduct, double totalRevenue) {
        return new PayLoadTotalStore(totalProduct, totalRevenue);
    }
}
