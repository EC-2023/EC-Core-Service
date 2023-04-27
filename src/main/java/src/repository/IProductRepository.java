
package src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import src.model.Product;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByNameContainingIgnoreCase(String keyword);

    @Query("SELECT sum(1) FROM Product s WHERE s.storeId = ?1 and s.isDeleted = false")
    Integer getCountProductByStore(UUID id);

    @Query(value = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(1) FROM Product r WHERE r.create_at BETWEEN :startDate AND :endDate  GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)", nativeQuery = true)
    List<Object[]> findMonthlyProductByDateBetween(Date startDate, Date endDate);

    @Query(value = "SELECT to_char(DATE_TRUNC('MONTH', r.create_at), 'Month') as month, SUM(1) FROM Product r WHERE r.create_at BETWEEN :startDate AND :endDate   GROUP BY DATE_TRUNC('MONTH', r.create_at) ORDER BY DATE_TRUNC('MONTH', r.create_at)", nativeQuery = true)
    List<Object[]> findYearlyProductByDateBetween(Date startDate, Date endDate);

    @Query(value = "SELECT DATE_TRUNC('day', r.create_at) as date, SUM(1) FROM Product r WHERE r.create_at BETWEEN :startDate AND :endDate  AND r.store_id = :storeId GROUP BY DATE_TRUNC('day', r.create_at) ORDER BY DATE_TRUNC('day', r.create_at)", nativeQuery = true)
    List<Object[]> findMonthlyProductByDateBetweenByStore(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") UUID storeId);

    @Query(value = "SELECT to_char(DATE_TRUNC('MONTH', r.create_at), 'Month') as month, SUM(1) FROM Product r WHERE r.create_at BETWEEN :startDate AND :endDate  AND r.store_id = :storeId GROUP BY DATE_TRUNC('MONTH', r.create_at) ORDER BY DATE_TRUNC('MONTH', r.create_at)", nativeQuery = true)
//    @Query(value = "SELECT new src.service.Statistic.Dtos.PayLoadStatisticData(CAST(FUNCTION('date_trunc', 'MONTH', r.createAt) as date), SUM(1)) FROM Product r WHERE r.createAt BETWEEN :startDate AND :endDate  AND r.storeId = :storeId GROUP BY EXTRACT(MONTH FROM r.createAt) ORDER BY  EXTRACT(MONTH FROM r.createAt)", nativeQuery = true)
    List<Object[]> findYearlyProductByDateBetweenByStore(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("storeId") UUID storeId);
}

