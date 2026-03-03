package com.group5.engagement.service.impl;

import com.group5.engagement.dto.request.CreateLoyaltyTierRequest;
import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.dto.response.LoyaltyTierResponse;
import com.group5.engagement.dto.response.TransactionHistoryResponse;
import com.group5.engagement.entity.CustomerFranchise;
import com.group5.engagement.entity.LoyaltyTier;
import com.group5.engagement.entity.PointTransaction;
import com.group5.engagement.exception.ResourceNotFoundException;
import com.group5.engagement.repository.CustomerFranchiseRepository;
import com.group5.engagement.repository.LoyaltyTierRepository;
import com.group5.engagement.repository.PointTransactionRepository;
import com.group5.engagement.service.LoyaltyService;
import org.springframework.transaction.annotation.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

    private final LoyaltyTierRepository tierRepository;
    private final CustomerFranchiseRepository customerFranchiseRepository;
    private final PointTransactionRepository pointTransactionRepository;
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
                .tierName(cf.getTier() != null ? cf.getTier().getName() : null)
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
                    .tierName(cf.getTier() != null ? cf.getTier().getName() : null)
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

    @Override
    public LoyaltyTierResponse createTier(CreateLoyaltyTierRequest request) {

        if (tierRepository.existsByFranchiseIdAndName(
                request.getFranchiseId(),
                request.getName())) {

            throw new IllegalArgumentException("Tier name already exists in this franchise");
        }

        int minPoints = switch (request.getName()) {
            case BRONZE -> 0;
            case SILVER -> 500;
            case GOLD -> 1000;
        };

        LoyaltyTier tier = LoyaltyTier.builder()
                .franchiseId(request.getFranchiseId())
                .name(request.getName())
                .minPoints(minPoints)   // 👈 auto set
                .tierMultiplier(request.getTierMultiplier())
                .benefits(request.getBenefits())
                .build();

        LoyaltyTier saved = tierRepository.save(tier);

        return LoyaltyTierResponse.builder()
                .id(saved.getId())
                .franchiseId(saved.getFranchiseId())
                .name(saved.getName())
                .minPoints(saved.getMinPoints())
                .tierMultiplier(saved.getTierMultiplier())
                .benefits(saved.getBenefits())
                .build();
    }
    @Override
    @Transactional(readOnly = true)
    public List<LoyaltyTierResponse> getAllTiers(Long franchiseId) {

        return tierRepository
                .findByFranchiseId(franchiseId)
                .stream()
                .sorted((t1, t2) -> t1.getMinPoints().compareTo(t2.getMinPoints()))
                .map(this::mapToTierResponse)
                .toList();
    }
    private LoyaltyTierResponse mapToTierResponse(LoyaltyTier tier) {
        return LoyaltyTierResponse.builder()
                .id(tier.getId())
                .franchiseId(tier.getFranchiseId())
                .name(tier.getName())
                .minPoints(tier.getMinPoints())
                .tierMultiplier(tier.getTierMultiplier())
                .benefits(tier.getBenefits())
                .build();
    }
    @Override
    @Transactional
    public LoyaltyTierResponse updateTier(Long tierId, CreateLoyaltyTierRequest request) {

        LoyaltyTier tier = tierRepository.findById(tierId)
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found"));

        // Không cho đổi franchiseId
        if (!tier.getFranchiseId().equals(request.getFranchiseId())) {
            throw new IllegalArgumentException("Cannot change franchiseId");
        }

        // Nếu đổi name
        if (request.getName() != null && request.getName() != tier.getName()) {

            boolean exists = tierRepository.existsByFranchiseIdAndName(
                    request.getFranchiseId(),
                    request.getName()
            );

            if (exists) {
                throw new IllegalArgumentException("Tier name already exists in this franchise");
            }

            tier.setName(request.getName());

            int minPoints = switch (request.getName()) {
                case BRONZE -> 0;
                case SILVER -> 500;
                case GOLD -> 1000;
            };

            tier.setMinPoints(minPoints);
        }

        tier.setTierMultiplier(request.getTierMultiplier());
        tier.setBenefits(request.getBenefits());

        return mapToTierResponse(tierRepository.save(tier));
    }

}
