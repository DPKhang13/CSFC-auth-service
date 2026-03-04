package com.group5.engagement.service.impl;

import com.group5.engagement.dto.request.ApplyCouponRequest;
import com.group5.engagement.dto.response.ApplyCouponResponse;
import com.group5.engagement.entity.Coupon;
import com.group5.engagement.entity.Promotion;
import com.group5.engagement.exception.coupon.CouponNotFoundException;
import com.group5.engagement.exception.coupon.InvalidCouponException;
import com.group5.engagement.repository.CouponRepository;
import com.group5.engagement.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public ApplyCouponResponse applyCoupon(ApplyCouponRequest req) {
        if(req.getCouponCode() == null || req.getCouponCode().isEmpty()) {
            throw new InvalidCouponException("Mã giá giảm giá không hợp lệ");
        }

       Coupon coupon = couponRepository
                .findByCode(req.getCouponCode())
                .orElseThrow(CouponNotFoundException::new);

        validateCoupon(coupon, req);

        double discount = calculateDiscount(coupon, req.getOrderAmount());

        double finalAmount = req.getOrderAmount() - discount;

        return new ApplyCouponResponse(
                req.getOrderAmount(),
                discount,
                finalAmount
        );
    }

    private double calculateDiscount(Coupon coupon, double amount) {

        double discount = 0;

        if ("FIXED_AMOUNT".equals(coupon.getDiscountType())) {
            discount = coupon.getDiscountValue();
        }

        if ("PERCENTAGE".equals(coupon.getDiscountType())) {

             discount = amount * coupon.getDiscountValue() / 100;

            if (coupon.getMaxDiscount() > 0) {
                discount = Math.min(discount, coupon.getMaxDiscount());
            }
        }

        return Math.min(discount, amount);
    }

    private void validateCoupon(Coupon coupon,
                                ApplyCouponRequest req) {
        LocalDateTime now = LocalDateTime.now();

        Promotion promotion = coupon.getPromotion();

        if ((coupon.getUsedCount() >= coupon.getUsageLimit() ||
                coupon.getUsageLimit() == 0) &&
                coupon.getUsageLimit() != null) {
            throw new InvalidCouponException("Mã giảm giá hết lượt dùng");
        }

        if(now.isAfter(promotion.getEndDate()) && promotion.getEndDate() != null) {
            throw new InvalidCouponException("Mã giảm giá đã hết hạn");
        }

        if(now.isBefore(promotion.getStartDate()) && promotion.getStartDate() != null) {
            throw new InvalidCouponException("Mã giảm giá chưa bắt đầu");
        }

        if (req.getOrderAmount() < coupon.getMinOrderValue()) {
            throw new InvalidCouponException("Tổng số tiền đơn hàng không đủ để áp dụng mã giảm giá");
        }

        if(!promotion.isActive()){
            throw new InvalidCouponException("Hiện không có chương trình khuyến mãi này!");
        }

        if(!coupon.isPublic()){
            throw new InvalidCouponException("Bạn không có quyền sử dụng mã giảm giá này!");
        }
    }

}
