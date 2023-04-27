package src.service.Statistic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import src.config.exception.NotFoundException;
import src.model.Store;
import src.repository.IOrdersRepository;
import src.repository.IProductRepository;
import src.repository.IStoreRepository;
import src.repository.IUserRepository;
import src.service.Statistic.Dtos.PayLoadStatisticData;
import src.service.Statistic.Dtos.PayLoadTotalStatistic;
import src.service.Statistic.Dtos.PayLoadTotalStore;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        switch (option) {
            case 0 -> {

                return CompletableFuture.completedFuture(getDailyRevenue(date, null));
            }
            case 1 -> {
                LocalDate startDate = YearMonth.from(localDate).atDay(1);
                LocalDate endDate = startDate.plusMonths(1);
                List<Object[]> rawResults = ordersRepository.findMonthlyRevenueByDateBetween(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                    Timestamp timestamp = (Timestamp) r[0];
                    return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
                }).collect(Collectors.toList()));
            }
            case 2 -> {
                 LocalDate startDate = localDate.minusYears(1);
                List<Object[]> rawResults = ordersRepository.findYearlyRevenueByDateBetween(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date);
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                            return new PayLoadStatisticData(r[0].toString(), Double.parseDouble(r[1].toString()));
                        }
                ).toList());
            }
        }
        return null;
    }

    @Override
    @Async
    public CompletableFuture<List<PayLoadStatisticData>> getStaticOrder(int option, Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        switch (option) {
            case 0 -> {
                return CompletableFuture.completedFuture(getDailyOrder(date, null));
            }
            case 1 -> {
                LocalDate startDate = YearMonth.from(localDate).atDay(1);
                LocalDate endDate = startDate.plusMonths(1);
                List<Object[]> rawResults = ordersRepository.findMonthlyOrder(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                    Timestamp timestamp = (Timestamp) r[0];
                    return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
                }).collect(Collectors.toList()));
            }
            case 2 -> {
                LocalDate startDate = localDate.minusYears(1);
                List<Object[]> rawResults = ordersRepository.findYearlyOrder(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date);
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                            return new PayLoadStatisticData(r[0].toString(), Double.parseDouble(r[1].toString()));
                        }
                ).toList());
            }
        }
        return null;
    }

    @Override
    @Async
    public CompletableFuture<List<PayLoadStatisticData>> getStaticRevenueByStore(int option, Date date, UUID userId) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        Store store = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Store not found"));
        switch (option) {
            case 0 -> {

                return CompletableFuture.completedFuture(getDailyRevenue(date, store.getId()));
            }
            case 1 -> {
                LocalDate startDate = YearMonth.from(localDate).atDay(1);
                LocalDate endDate = startDate.plusMonths(1);
                List<Object[]> rawResults = ordersRepository.findMonthlyRevenueByDateBetweenByStore(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        store.getId());
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                    Timestamp timestamp = (Timestamp) r[0];
                    return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
                }).collect(Collectors.toList()));
            }
            case 2 -> {
                LocalDate startDate = localDate.minusYears(1);
                List<Object[]> rawResults = ordersRepository.findYearlyRevenueByDateBetweenByStore(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date, store.getId());
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                            return new PayLoadStatisticData(r[0].toString(), Double.parseDouble(r[1].toString()));
                        }
                ).toList());
            }
        }
        return null;
    }

    @Override
    @Async
    public CompletableFuture<List<PayLoadStatisticData>> getStaticOrderByStore(int option, Date date, UUID userId) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        Store store = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Store not found"));
        switch (option) {
            case 0 -> {

                return CompletableFuture.completedFuture(getDailyOrder(date, store.getId()));
            }
            case 1 -> {
                LocalDate startDate = YearMonth.from(localDate).atDay(1);
                LocalDate endDate = startDate.plusMonths(1);
                List<Object[]> rawResults = ordersRepository.findMonthlyOrderByStore(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        store.getId());
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                    Timestamp timestamp = (Timestamp) r[0];
                    return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
                }).collect(Collectors.toList()));
            }
            case 2 -> {
                LocalDate startDate = localDate.minusYears(1);
                List<Object[]> rawResults = ordersRepository.findYearlyOrderByStore(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date, store.getId());
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                            return new PayLoadStatisticData(r[0].toString(), Double.parseDouble(r[1].toString()));
                        }
                ).toList());
            }
        }
        return null;
    }


    @Async
    @Override
    public CompletableFuture<List<PayLoadStatisticData>> getStaticProduct(int option, Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        switch (option) {
            case 0 -> {
                return CompletableFuture.completedFuture(getDailyProduct(date, null));
            }
            case 1 -> {
                LocalDate startDate = YearMonth.from(localDate).atDay(1);
                LocalDate endDate = startDate.plusMonths(1);
                List<Object[]> rawResults = productRepository.findMonthlyProductByDateBetween(
                        Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                        , Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                    Timestamp timestamp = (Timestamp) r[0];
                    return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
                }).collect(Collectors.toList()));
            }
            case 2 -> {
                LocalDate startDate = localDate.minusYears(1);
                List<Object[]> rawResults = productRepository.findYearlyProductByDateBetween(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date);
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                            return new PayLoadStatisticData(r[0].toString(), Double.parseDouble(r[1].toString()));
                        }
                ).toList());
            }
        }
        return null;
    }

    @Async
    @Override
    public CompletableFuture<List<PayLoadStatisticData>> getStaticProductByStore(int option, Date date, UUID userId) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        Store store = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Store not found"));
        switch (option) {
            case 0 -> {
                return CompletableFuture.completedFuture(getDailyProduct(date, store.getId()));
            }
            case 1 -> {
                LocalDate startDate = YearMonth.from(localDate).atDay(1);
                LocalDate endDate = startDate.plusMonths(1);
                List<Object[]> rawResults = productRepository.findMonthlyProductByDateBetweenByStore(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                        , Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), store.getId());
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                    Timestamp timestamp = (Timestamp) r[0];
                    return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
                }).collect(Collectors.toList()));
            }
            case 2 -> {
                LocalDate startDate = localDate.minusYears(1);
                List<Object[]> rawResults = productRepository.findYearlyProductByDateBetweenByStore(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), date, store.getId());
                return CompletableFuture.completedFuture(rawResults.stream().map(r -> {
                            return new PayLoadStatisticData(r[0].toString(), Double.parseDouble(r[1].toString()));
                        }
                ).toList());
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


    public List<PayLoadStatisticData> getDailyRevenue(Date date, UUID storeId) {
        Instant instant = date.toInstant();
        LocalDate startDate = instant.atZone(ZoneId.systemDefault()).toLocalDate().minusDays(7);
        String queryStr;
        Query query;
        if (storeId == null) {
            queryStr = "SELECT DATE_TRUNC('day', r.create_at) AS date, SUM(r.amount_togd) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate  GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)";
            query = entityManager.createNativeQuery(queryStr);
        } else {
            queryStr = "SELECT DATE_TRUNC('day', r.create_at) AS date, SUM(r.amount_togd) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.store_id= :storeId  GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)";
            query = entityManager.createNativeQuery(queryStr);
            query.setParameter("storeId", storeId);
        }
        query.setParameter("startDate", Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        query.setParameter("endDate", date);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream().map(r -> {
            Timestamp timestamp = (Timestamp) r[0];
            return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
        }).collect(Collectors.toList());
    }

    public List<PayLoadStatisticData> getDailyOrder(Date date, UUID storeId) {
        Instant instant = date.toInstant();
        LocalDate startDate = instant.atZone(ZoneId.systemDefault()).toLocalDate().minusDays(7);
        String queryStr;
        Query query;
        if (storeId == null) {
            queryStr = "SELECT DATE_TRUNC('day', r.create_at) AS date, SUM(1) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate  GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)";
            query = entityManager.createNativeQuery(queryStr);
        } else {
            queryStr = "SELECT DATE_TRUNC('day', r.create_at) AS date,  SUM(1) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.store_id= :storeId  GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)";
            query = entityManager.createNativeQuery(queryStr);
            query.setParameter("storeId", storeId);
        }
        query.setParameter("startDate", Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        query.setParameter("endDate", date);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream().map(r -> {
            Timestamp timestamp = (Timestamp) r[0];
            return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
        }).collect(Collectors.toList());
    }

    public List<PayLoadStatisticData> getDailyProduct(Date date, UUID storeId) {
        Instant instant = date.toInstant();
        LocalDate startDate = instant.atZone(ZoneId.systemDefault()).toLocalDate().minusDays(7);
        String queryStr;
        Query query;
        if (storeId == null) {
            queryStr = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(1) FROM product r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.is_deleted = false GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)";
            query = entityManager.createNativeQuery(queryStr);
        } else {
            queryStr = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(1) FROM Product r WHERE r.create_at BETWEEN :startDate AND :endDate  AND r.store_id = :storeId GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)";
            query = entityManager.createNativeQuery(queryStr);
            query.setParameter("storeId", storeId);
        }
        query.setParameter("startDate", Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        query.setParameter("endDate", date);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream().map(r -> {
            Timestamp timestamp = (Timestamp) r[0];
            return new PayLoadStatisticData(new Date(timestamp.getTime()), Double.parseDouble(r[1].toString()));
        }).collect(Collectors.toList());
    }


    public CompletableFuture<PayLoadTotalStore> getTotalProductAndRevenueStore(UUID userId) {
        Store store = storeRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Store not found"));
        Integer valueTotal = productRepository.getCountProductByStore(store.getId());
        Double valueRevenue = ordersRepository.getRevenueByStore(store.getId());
        if (valueRevenue == null) {
            valueRevenue = 0.0;
        }
        if (valueTotal == null) {
            valueTotal = 0;
        }
        return CompletableFuture.completedFuture(PayLoadTotalStore.create(
                valueTotal,
                valueRevenue
        ));
    }

}
