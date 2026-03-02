package com.group5.engagement.repository;

import com.group5.engagement.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // Tìm coupon theo mã code (VD: "CHUC_MUNG_NAM_MOI")
    Optional<Coupon> findByCode(String code);
}