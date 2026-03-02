package com.group5.engagement.service.impl;

import com.group5.engagement.dto.request.ApplyCouponRequest;
import com.group5.engagement.dto.response.ApplyCouponResponse;
import com.group5.engagement.entity.Coupon;
import com.group5.engagement.repository.CouponRepository;
import com.group5.engagement.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public ApplyCouponResponse applyCoupon(ApplyCouponRequest req) {
        Coupon coupon = couponRepository
                .findByCode(req.getCouponCode())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new RuntimeException("Coupon hết lượt dùng");
        }

        double discount = calculateDiscount(coupon, req.getOrderAmount());

        double finalAmount = req.getOrderAmount() - discount;

        return new ApplyCouponResponse(
                req.getOrderAmount(),
                discount,
                finalAmount
        );
    }

    private double calculateDiscount(Coupon coupon, double amount) {

        if ("FIXED_AMOUNT".equals(coupon.getDiscountType())) {
            return coupon.getDiscountValue();
        }

        if ("PERCENTAGE".equals(coupon.getDiscountType())) {

            return amount * coupon.getDiscountValue() / 100;
        }

        return 0;
    }

}
