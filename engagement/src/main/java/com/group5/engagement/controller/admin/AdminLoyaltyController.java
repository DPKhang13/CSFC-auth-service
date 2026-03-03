package com.group5.engagement.controller.admin;

import com.group5.engagement.dto.request.CreateLoyaltyTierRequest;
import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.dto.response.LoyaltyTierResponse;
import com.group5.engagement.service.LoyaltyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin Loyalty", description = "Quản lý hệ thống Loyalty & Tier khách hàng")
@RestController
@RequestMapping("/api/v1/admin/loyalty")
@RequiredArgsConstructor
public class AdminLoyaltyController {

    private final LoyaltyService loyaltyService;

//    @Operation(summary = "Lấy danh sách khách hàng theo bộ lọc")
//    @GetMapping("/customers")
//    public ResponseEntity<Page<CustomerEngagementResponse>> getAllCustomers(
//            @Parameter(description = "Filter theo franchise ID")
//            @RequestParam(required = false) Long franchiseId,
//
//            @Parameter(description = "Filter theo tier ID")
//            @RequestParam(required = false) Long tierId,
//
//            @Parameter(description = "Page number (0-indexed)")
//            @RequestParam(defaultValue = "0") int page,
//
//            @Parameter(description = "Page size")
//            @RequestParam(defaultValue = "20") int size) {
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Page<CustomerEngagementResponse> customers =
//                loyaltyService.getAllCustomers(franchiseId, tierId, pageable);
//
//        return ResponseEntity.ok(customers);
//    }

    @Operation(
            summary = "Tạo Loyalty Tier cố định",
            description = """
            Hệ thống Loyalty sử dụng 3 cấp bậc cố định:

            - BRONZE  → minPoints = 0
            - SILVER  → minPoints = 500
            - GOLD    → minPoints = 1000

            Lưu ý:
            - minPoints được hệ thống tự động thiết lập theo name.
            - Admin chỉ cần truyền: franchiseId, name, tierMultiplier, benefits.
            - Không được tạo trùng tier trong cùng franchise.
            """
    )
    @PostMapping("/tiers")
    public ResponseEntity<LoyaltyTierResponse> createTier(
            @RequestBody CreateLoyaltyTierRequest request) {

        LoyaltyTierResponse response = loyaltyService.createTier(request);
        return ResponseEntity.ok(response);
    }
}