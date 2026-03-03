package com.group5.engagement.controller.customer;

import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.dto.response.TransactionHistoryResponse;
import com.group5.engagement.service.LoyaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loyalty")
@RequiredArgsConstructor
public class CustomerLoyaltyController {
    private final LoyaltyService loyaltyService;

    @GetMapping("/customers/{customerId}/franchise/{franchiseId}")
    public ResponseEntity<CustomerEngagementResponse> getCustomerEngagement(
            @PathVariable Long customerId,
            @PathVariable Long franchiseId) {
        CustomerEngagementResponse response = loyaltyService.getCustomerEngagement(customerId, franchiseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers/{customerId}/franchise/{franchiseId}/transactions")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistory(
            @PathVariable Long customerId,
            @PathVariable Long franchiseId) {
        List<TransactionHistoryResponse> transactions = loyaltyService.getTransactionHistory(customerId, franchiseId);
        return ResponseEntity.ok(transactions);
    }
}
