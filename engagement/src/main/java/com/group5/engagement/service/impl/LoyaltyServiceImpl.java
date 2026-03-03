package com.group5.engagement.service.impl;

import com.group5.engagement.dto.request.RedeemRequest;
import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.dto.response.RedeemResponse;
import com.group5.engagement.dto.response.TransactionHistoryResponse;
import com.group5.engagement.entity.CustomerFranchise;
import com.group5.engagement.entity.PointTransaction;
import com.group5.engagement.entity.Reward;
import com.group5.engagement.exception.ResourceNotFoundException;
import com.group5.engagement.repository.CustomerFranchiseRepository;
import com.group5.engagement.repository.PointTransactionRepository;
import com.group5.engagement.repository.RewardRepository;
import com.group5.engagement.service.LoyaltyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

    private final CustomerFranchiseRepository customerFranchiseRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final RewardRepository rewardRepository;

    @Override
    public CustomerEngagementResponse getCustomerEngagement(Long customerId, Long franchiseId) {
        CustomerFranchise cf = customerFranchiseRepository
                .findByCustomerIdAndFranchiseId(customerId, franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found in this franchise"));

        return CustomerEngagementResponse.builder()
                .id(cf.getId())
                .customerId(cf.getCustomerId())
                .franchiseId(cf.getFranchiseId())
                .currentPoints(cf.getCurrentPoints())
                .totalEarnedPoints(cf.getTotalEarnedPoints())
                .tierName(cf.getTier() != null ? cf.getTier().getName() : "No Tier")
                .status(cf.getStatus())
                .firstOrderAt(cf.getFirstOrderAt())
                .lastOrderAt(cf.getLastOrderAt())
                .createdAt(cf.getCreatedAt())
                .build();
    }

    @Override
    public List<TransactionHistoryResponse> getTransactionHistory(Long customerId, Long franchiseId) {
        CustomerFranchise cf = customerFranchiseRepository
                .findByCustomerIdAndFranchiseId(customerId, franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found in this franchise"));

        List<PointTransaction> transactions = pointTransactionRepository
                .findAllByCustomerFranchiseIdOrderByCreatedAtDesc(cf.getId());

        return transactions.stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<@NonNull CustomerEngagementResponse> getAllCustomers(Long franchiseId, Long tierId, Pageable pageable) {
        Page<CustomerFranchise> customers = customerFranchiseRepository
                .findByFilters(franchiseId, tierId, pageable);

        return customers.map(cf -> {
            return CustomerEngagementResponse.builder()
                    .id(cf.getId())
                    .customerId(cf.getCustomerId())
                    .franchiseId(cf.getFranchiseId())
                    .currentPoints(cf.getCurrentPoints())
                    .totalEarnedPoints(cf.getTotalEarnedPoints())
                    .tierName(cf.getTier() != null ? cf.getTier().getName() : "No Tier")
                    .status(cf.getStatus())
                    .firstOrderAt(cf.getFirstOrderAt())
                    .lastOrderAt(cf.getLastOrderAt())
                    .createdAt(cf.getCreatedAt())
                    .build();
        });
    }


    // Helper method
    private TransactionHistoryResponse mapToTransactionResponse(PointTransaction pt) {
        return TransactionHistoryResponse.builder()
                .id(pt.getId())
                .amount(pt.getAmount())
                .actionType(pt.getActionType())
                .referenceId(pt.getReferenceId())
                .createdAt(pt.getCreatedAt())
                .expiryDate(pt.getExpiryDate())
                .build();
    }

    @Transactional
    public RedeemResponse redeem(RedeemRequest request) {

        CustomerFranchise customerFranchise = customerFranchiseRepository
                .findById(request.getCustomerFranchiseId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Reward reward = rewardRepository
                .findById(request.getRewardId())
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        if(!reward.getIsActive()){
            throw new ResourceNotFoundException("Reward is not active");
        }

        if(customerFranchise.getCurrentPoints() < reward.getRequiredPoints()){
            throw new ResourceNotFoundException("Not enough loyalty points ");
        }

        customerFranchise.setCurrentPoints(customerFranchise.getCurrentPoints() - reward.getRequiredPoints());
        customerFranchiseRepository.save(customerFranchise);
        return new RedeemResponse(

        );

    }

}
