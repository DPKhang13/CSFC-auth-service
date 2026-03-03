package com.group5.engagement.service;

import com.group5.engagement.dto.request.ApplyCouponRequest;
import com.group5.engagement.dto.response.ApplyCouponResponse;

public interface CouponService {
    ApplyCouponResponse applyCoupon(ApplyCouponRequest request);
}
