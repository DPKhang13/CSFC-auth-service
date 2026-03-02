package com.group5.engagement.service.impl;

import com.group5.engagement.dto.request.ApplyCouponRequest;
import com.group5.engagement.dto.response.ApplyCouponResponse;
import com.group5.engagement.entity.Coupon;
import com.group5.engagement.exception.coupon.CouponNotFoundException;
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
                .orElseThrow(CouponNotFoundException::new);

        if (coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new RuntimeException("Phiếu giảm giá hết lượt dùng");
        }

        if (req.getOrderAmount() < coupon.getMinOrderValue()) {
            throw new RuntimeException("Tổng số tiền đơn hàng không đủ để áp dụng phiếu giảm giá");
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

            double discount = amount * coupon.getDiscountValue() / 100;

            if (coupon.getMaxDiscount() != 0) {
                discount = Math.min(discount, coupon.getMaxDiscount());
            }

            return discount;
        }

        return 0;
    }

}
