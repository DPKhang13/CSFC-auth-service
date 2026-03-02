package com.group5.engagement.repository;

import com.group5.engagement.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    // Query kiểm tra xem có promotion nào đang ACTIVE mà trùng thời gian không
    // Logic overlapping: (StartA <= EndB) and (EndA >= StartB)
    @Query("SELECT COUNT(p) > 0 FROM Promotion p " +
           "WHERE p.franchiseId = :franchiseId " +
           "AND (p.status = 'ACTIVE' OR p.status = 'DRAFT') " +
           "AND (:startDate <= p.endDate AND :endDate >= p.startDate)")
    boolean existsOverlappingPromotion(@Param("franchiseId") Long franchiseId, 
                                       @Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate);
}