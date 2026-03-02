package com.group5.engagement.controller.customer;

import com.group5.engagement.dto.request.ApplyCouponRequest;
import com.group5.engagement.dto.response.ApplyCouponResponse;
import com.group5.engagement.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("/apply")
    public ApplyCouponResponse apply(@RequestBody ApplyCouponRequest req){
        return couponService.applyCoupon(req);
    }
}
