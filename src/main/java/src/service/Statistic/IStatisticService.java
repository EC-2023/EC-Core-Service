package src.service.Statistic;

import src.service.Statistic.Dtos.PayLoadStatisticData;
import src.service.Statistic.Dtos.PayLoadTotalStatistic;
import src.service.Statistic.Dtos.PayLoadTotalStore;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IStatisticService {
    public CompletableFuture<PayLoadTotalStatistic> getTotalStatistic();
    public CompletableFuture<List<PayLoadStatisticData>> getStaticRevenue(int option, Date date);
    public CompletableFuture<List<PayLoadStatisticData>> getStaticUser(int option, Date date);
    public CompletableFuture<List<PayLoadStatisticData>> getStaticStore(int option, Date date);
    public CompletableFuture<List<PayLoadStatisticData>> getStaticProduct(int option, Date date);
    public  CompletableFuture<PayLoadTotalStore> getTotalProductAndRevenueStore(UUID userId);
}
