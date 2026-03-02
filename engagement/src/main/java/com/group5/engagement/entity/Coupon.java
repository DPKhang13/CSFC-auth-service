package com.group5.engagement.entity;

import com.group5.engagement.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "discount_type")
    private String discountType; // PERCENT, FIXED_AMOUNT

    @Column(name = "discount_value")
    private Double discountValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "min_tier_id")
    private LoyaltyTier minTier; // Chỉ hạng này mới dùng được

    @Column(name = "usage_limit")
    private Integer usageLimit; // Tổng số lần dùng toàn hệ thống

    @Column(name = "used_count")
    private Integer usedCount = 0;
}