package com.group5.engagement.service;

import com.group5.engagement.dto.request.CreateLoyaltyTierRequest;
import com.group5.engagement.dto.request.RedeemRequest;
import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.dto.response.LoyaltyTierResponse;
import com.group5.engagement.dto.response.RedeemResponse;
import com.group5.engagement.dto.response.TransactionHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoyaltyService {

    CustomerEngagementResponse getCustomerEngagement(Long customerId, Long franchiseId);

    List<TransactionHistoryResponse> getTransactionHistory(Long customerId, Long franchiseId);

    Page<CustomerEngagementResponse> getAllCustomers(
            Long franchiseId,
            Long tierId,
            Pageable pageable
    );

    // ===== Loyalty Tier =====
    LoyaltyTierResponse createTier(CreateLoyaltyTierRequest request);
    List<LoyaltyTierResponse> getAllTiers(Long franchiseId);
    LoyaltyTierResponse updateTier(Long tierId, CreateLoyaltyTierRequest request);

    // ===== Redeem =====
    RedeemResponse redeem(RedeemRequest redeemRequest);
}