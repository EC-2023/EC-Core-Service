
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.model.Orders;
import src.service.Statistic.Dtos.PayLoadStatisticData;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT o FROM Orders o WHERE o.userId = ?1")
    List<Orders> getMyOrders(UUID id);
    @Query("SELECT new src.service.Statistic.Dtos.PayLoadStatisticData(MONTH(r.createAt), SUM(r.amountToGd)) FROM Orders r WHERE r.createAt BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY MONTH(r.createAt) ORDER BY MONTH(r.createAt)")
    List<PayLoadStatisticData> findMonthlyRevenueByDateBetween(Date startDate, Date endDate);
    @Query("SELECT new src.service.Statistic.Dtos.PayLoadStatisticData(YEAR(r.createAt), SUM(r.amountToGd)) FROM Orders r WHERE r.createAt BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY YEAR(r.createAt) ORDER BY YEAR(r.createAt)")
    List<PayLoadStatisticData> findYearlyRevenueByDateBetween(Date startDate, Date endDate);
    @Query("SELECT sum(s.amountToStore) FROM Orders s WHERE s.storeId = ?1 and s.isDeleted = false and s.status = 3")
    Double getRevenueByStore(UUID id);
    @Query("SELECT COUNT(o) FROM Orders o WHERE o.code = ?1")
    Long countByOrderNumber(String orderNumber);
}

    