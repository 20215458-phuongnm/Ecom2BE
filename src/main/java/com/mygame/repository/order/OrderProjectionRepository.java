package com.mygame.repository.order;

import com.mygame.dto.statistic.StatisticResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class OrderProjectionRepository {

    private final EntityManager em;

    // Đếm số lượng đơn hàng theo ngày tạo
    public List<StatisticResource> getOrderCountByCreateDate() {
        try {
            String sql = "SELECT DATE(created_at) AS created_date, COUNT(id) " +
                    "FROM `order` " +
                    "GROUP BY DATE(created_at) " +
                    "ORDER BY DATE(created_at)";

            Query nativeQuery = em.createNativeQuery(sql);

            @SuppressWarnings("unchecked")
            List<Object[]> results = nativeQuery.getResultList();

            return results.stream()
                    .map(row -> {
                        Date sqlDate = (Date) row[0];
                        Long count = ((Number) row[1]).longValue();

                        // ✅ Convert java.sql.Date → LocalDate → Instant safely
                        Instant instantDate = sqlDate.toLocalDate()
                                .atStartOfDay(java.time.ZoneId.systemDefault())
                                .toInstant();

                        return new StatisticResource(instantDate, count);
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thống kê đơn hàng theo ngày tạo", e);
        }
    }
}
