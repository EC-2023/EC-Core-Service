package src.service.Statistic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.repository.IOrdersRepository;
import src.repository.IProductRepository;
import src.repository.IStoreRepository;
import src.repository.IUserRepository;
import src.service.Statistic.Dtos.PayLoadStatisticData;
import src.service.Statistic.Dtos.PayLoadTotalStatistic;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StatisticService implements IStatisticService {
    final
    IUserRepository userRepository;
    final
    IProductRepository productRepository;
    final
    IStoreRepository storeRepository;
    final
    IOrdersRepository ordersRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public StatisticService(IUserRepository userRepository, IProductRepository productRepository, IStoreRepository storeRepository, IOrdersRepository ordersRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    @Async
    public CompletableFuture<PayLoadTotalStatistic> getTotalStatistic() {
        return CompletableFuture.completedFuture(PayLoadTotalStatistic.create(
                productRepository.count(),
                storeRepository.count(),
                userRepository.count()
        ));
    }

    // TODO:  0 is week, 1 is month, 2 is year
    @Override
    @Async
    public CompletableFuture<List<PayLoadStatisticData>> getStaticRevenue(int option, Date date) {
        LocalDate startDate;
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        switch (option) {
            case 0 -> {
//                startDate = date.minusDays(7);
                return CompletableFuture.completedFuture(getDailyRevenue(date));
            }
            case 1 -> {
                startDate = localDate.minusMonths(1);
                return CompletableFuture.completedFuture(ordersRepository.findMonthlyRevenueByDateBetween(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date));
            }
            case 2 -> {
                startDate = localDate.minusYears(1);
                return CompletableFuture.completedFuture(ordersRepository.findYearlyRevenueByDateBetween(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date));
            }
        }
        return null;
    }

    @Override
    @Async
    public CompletableFuture<List<PayLoadStatisticData>> getStaticUser(int option, Date date) {
        return null;
    }

    @Override
    @Async
    public CompletableFuture<List<PayLoadStatisticData>> getStaticStore(int option, Date date) {
        return null;
    }

    @Override
    @Async
    public CompletableFuture<List<PayLoadStatisticData>> getStaticProduct(int option, Date date) {
        return null;
    }

    public List<PayLoadStatisticData> getDailyRevenue(Date date) {
        Instant instant = date.toInstant();
        LocalDate startDate =  instant.atZone(ZoneId.systemDefault()).toLocalDate().minusDays(7);
        String queryStr = "SELECT NEW src.service.Statistic.Dtos.PayLoadStatisticData(r.createAt, SUM(r.amountToGd)) FROM Orders r WHERE r.createAt BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY r.createAt ORDER BY r.createAt";
        TypedQuery<PayLoadStatisticData> query = entityManager.createQuery(queryStr, PayLoadStatisticData.class);
        query.setParameter("startDate", Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        query.setParameter("endDate", date);
        return query.getResultList();
    }

}
