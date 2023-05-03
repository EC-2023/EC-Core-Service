
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import src.model.Orders;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT o FROM Orders o WHERE o.userId = ?1")
    List<Orders> getMyOrders(UUID id);


    @Query("SELECT sum(s.amountToStore) FROM Orders s WHERE s.storeId = ?1 and s.isDeleted = false and s.status = 3")
    Double getRevenueByStore(UUID id);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.code = ?1")
    Long countByOrderNumber(String orderNumber);

    @Query(value = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(r.amount_togd) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)", nativeQuery = true)
    List<Object[]> findMonthlyRevenueByDateBetween(Date startDate, Date endDate);

    @Query(value = "SELECT to_char(DATE_TRUNC('MONTH', r.create_at), 'Month') as month, SUM(r.amount_togd) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.status = 2 GROUP BY DATE_TRUNC('MONTH', r.create_at) ORDER BY DATE_TRUNC('MONTH', r.create_at)", nativeQuery = true)
    List<Object[]> findYearlyRevenueByDateBetween(Date startDate, Date endDate);

    @Query(value = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(r.amount_to_store) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.status = 2 AND r.store_id= :storeId GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)", nativeQuery = true)
//    @Query("SELECT new src.service.Statistic.Dtos.PayLoadStatisticData(r.createAt, SUM(r.amountToGd)) FROM Orders r WHERE r.createAt BETWEEN :startDate AND :endDate AND r.status = 2 AND r.storeId = :storeId GROUP BY EXTRACT(DATE FROM r.createAt) ORDER BY DATE(r.createAt)")
    List<Object[]> findMonthlyRevenueByDateBetweenByStore(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") UUID storeId);

    @Query(value = "SELECT to_char(DATE_TRUNC('MONTH', r.create_at), 'Month') as month, SUM(r.amount_to_store) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.status = 2 AND r.store_id = :storeId GROUP BY DATE_TRUNC('MONTH', r.create_at) ORDER BY DATE_TRUNC('MONTH', r.create_at)", nativeQuery = true)
    List<Object[]> findYearlyRevenueByDateBetweenByStore(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") UUID storeId);

    @Query(value = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(1) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.store_id = :storeId GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)", nativeQuery = true)
    List<Object[]> findMonthlyOrderByStore(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") UUID storeId);

    @Query(value = "SELECT to_char(DATE_TRUNC('MONTH', r.create_at), 'Month') as month, SUM(1) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate AND r.store_id = :storeId GROUP BY DATE_TRUNC('MONTH', r.create_at) ORDER BY DATE_TRUNC('MONTH', r.create_at)", nativeQuery = true)
    List<Object[]> findYearlyOrderByStore(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") UUID storeId);

    @Query(value = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(1) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)", nativeQuery = true)
    List<Object[]> findMonthlyOrder(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT to_char(DATE_TRUNC('MONTH', r.create_at), 'Month') as month, SUM(1) FROM orders r WHERE r.create_at BETWEEN :startDate AND :endDate GROUP BY DATE_TRUNC('MONTH', r.create_at) ORDER BY DATE_TRUNC('MONTH', r.create_at)", nativeQuery = true)
    List<Object[]> findYearlyOrder(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT EXISTS(SELECT 1 FROM orders o INNER JOIN order_items oi ON o.order_id = oi.order_id WHERE o.user_id = :userId AND oi.product_id = :productId and o.status = 3 LIMIT 1)", nativeQuery = true)
    boolean checkUserPurchasedProduct(@Param("userId") UUID userId, @Param("productId") UUID productId);
}

