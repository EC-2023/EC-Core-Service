
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.Orders;
import src.service.Statistic.Dtos.PayLoadStatisticData;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT o FROM Orders o WHERE o.userId = ?1")
    List<Orders> getMyOrders(UUID id);

    //    @Query("SELECT new map(MONTH(r.date) as month, SUM(r.value) as total) FROM Revenue r WHERE r.date BETWEEN :startDate AND :endDate GROUP BY MONTH(r.date) ORDER BY month")
//    List<Map<String, Object>> findMonthlyRevenueByDateBetween(LocalDate startDate, LocalDate endDate);
//
//    @Query("SELECT new map(YEAR(r.createAt) as year, SUM(r.value) as total) FROM Orders r WHERE r.createAt BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY YEAR(r.date) ORDER BY year")
//    List<Map<String, Object>> findYearlyRevenueByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT new src.service.Statistic.Dtos.PayLoadStatisticData(MONTH(r.createAt), SUM(r.amountToGd)) FROM Orders r WHERE r.createAt BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY MONTH(r.createAt) ORDER BY MONTH(r.createAt)")
    List<PayLoadStatisticData> findMonthlyRevenueByDateBetween(Date startDate, Date endDate);

    @Query("SELECT new src.service.Statistic.Dtos.PayLoadStatisticData(YEAR(r.createAt), SUM(r.amountToGd)) FROM Orders r WHERE r.createAt BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY YEAR(r.createAt) ORDER BY YEAR(r.createAt)")
    List<PayLoadStatisticData> findYearlyRevenueByDateBetween(Date startDate, Date endDate);


    @Query("SELECT sum(s.amountToStore) FROM Orders s WHERE s.storeId = ?1 and s.isDeleted = false and s.status = 2")
    Optional<Double> getRevenueByStore(UUID id);
}

    