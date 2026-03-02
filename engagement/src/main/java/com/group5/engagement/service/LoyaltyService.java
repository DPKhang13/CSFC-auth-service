package com.group5.engagement.service;

import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.dto.response.TransactionHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LoyaltyService {

    CustomerEngagementResponse getCustomerEngagement(Long customerId, Long franchiseId);

    List<TransactionHistoryResponse> getTransactionHistory(Long customerId, Long franchiseId);

    Page<CustomerEngagementResponse> getAllCustomers(
            Long franchiseId,
            Long tierId,
            Pageable pageable
    );
}
