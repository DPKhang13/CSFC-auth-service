package com.group5.engagement.controller.admin;

import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.service.LoyaltyService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/loyalty")
@RequiredArgsConstructor
public class AdminLoyaltyController {
    private final LoyaltyService loyaltyService;

    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerEngagementResponse>> getAllCustomers(
            @Parameter(description = "Filter by franchise ID") @RequestParam(required = false) Long franchiseId,
            @Parameter(description = "Filter by tier ID") @RequestParam(required = false) Long tierId,
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CustomerEngagementResponse> customers = loyaltyService.getAllCustomers(franchiseId, tierId, pageable);
        return ResponseEntity.ok(customers);
    }
}
