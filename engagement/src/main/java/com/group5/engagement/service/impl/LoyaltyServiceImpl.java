package com.group5.engagement.service.impl;

import com.group5.engagement.constants.ActionType;
import com.group5.engagement.dto.request.CreateLoyaltyTierRequest;
import com.group5.engagement.dto.request.RedeemRequest;
import com.group5.engagement.dto.response.CustomerEngagementResponse;
import com.group5.engagement.dto.response.LoyaltyTierResponse;
import com.group5.engagement.dto.response.RedeemResponse;
import com.group5.engagement.dto.response.TransactionHistoryResponse;
import com.group5.engagement.entity.CustomerFranchise;
import com.group5.engagement.entity.LoyaltyTier;
import com.group5.engagement.entity.PointTransaction;
import com.group5.engagement.entity.Reward;
import com.group5.engagement.exception.ResourceNotFoundException;
import com.group5.engagement.repository.CustomerFranchiseRepository;
import com.group5.engagement.repository.LoyaltyTierRepository;
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

    private final LoyaltyTierRepository tierRepository;
    private final CustomerFranchiseRepository customerFranchiseRepository;
    private final PointTransactionRepository pointTransactionRepository;
    private final RewardRepository rewardRepository;

    // ================= CUSTOMER =================

    @Override
    public CustomerEngagementResponse getCustomerEngagement(Long customerId, Long franchiseId) {
        CustomerFranchise cf = customerFranchiseRepository
                .findByCustomerIdAndFranchiseId(customerId, franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found in this franchise"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found in this franchise"));

        return pointTransactionRepository
                .findAllByCustomerFranchiseIdOrderByCreatedAtDesc(cf.getId())
                .stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<@NonNull CustomerEngagementResponse> getAllCustomers(
            Long franchiseId,
            Long tierId,
            Pageable pageable) {

        return customerFranchiseRepository
                .findByFilters(franchiseId, tierId, pageable)
                .map(cf -> CustomerEngagementResponse.builder()
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
                        .build());
    }

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

    // ================= TIER MANAGEMENT =================

    @Override
    @Transactional
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
                .minPoints(minPoints)
                .tierMultiplier(request.getTierMultiplier())
                .benefits(request.getBenefits())
                .build();

        return mapToTierResponse(tierRepository.save(tier));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoyaltyTierResponse> getAllTiers(Long franchiseId) {
        return tierRepository.findByFranchiseId(franchiseId)
                .stream()
                .sorted((t1, t2) -> t1.getMinPoints().compareTo(t2.getMinPoints()))
                .map(this::mapToTierResponse)
                .toList();
    }

    @Override
    @Transactional
    public LoyaltyTierResponse updateTier(Long tierId, CreateLoyaltyTierRequest request) {

        LoyaltyTier tier = tierRepository.findById(tierId)
                .orElseThrow(() -> new ResourceNotFoundException("Tier not found"));

        if (!tier.getFranchiseId().equals(request.getFranchiseId())) {
            throw new IllegalArgumentException("Cannot change franchiseId");
        }

        if (request.getName() != null && request.getName() != tier.getName()) {

            if (tierRepository.existsByFranchiseIdAndName(
                    request.getFranchiseId(),
                    request.getName())) {
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

    // ================= REDEEM =================

    @Override
    @Transactional
    public RedeemResponse redeem(RedeemRequest request) {

        CustomerFranchise customerFranchise = customerFranchiseRepository
                .findByCustomerIdForUpdate(request.getCustomerFranchiseId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Reward reward = rewardRepository.findById(request.getRewardId())
                .orElseThrow(() -> new RuntimeException("Reward not found"));

        if (!reward.getIsActive()) {
            throw new ResourceNotFoundException("Reward is not active");
        }

        if (!reward.getFranchiseId()
                .equals(customerFranchise.getFranchiseId())) {
            throw new IllegalArgumentException("Reward does not belong to this franchise");
        }

        if (customerFranchise.getCurrentPoints() < reward.getRequiredPoints()) {
            throw new ResourceNotFoundException("Not enough loyalty points");
        }

        int remainingPoints = customerFranchise.getCurrentPoints() - reward.getRequiredPoints();
        customerFranchise.setCurrentPoints(remainingPoints);
        customerFranchiseRepository.save(customerFranchise);

        PointTransaction pointTransaction = PointTransaction.builder()
                .customerFranchise(customerFranchise)
                .amount(-reward.getRequiredPoints())
                .actionType(ActionType.REDEEM)
                .referenceId("REWARD" + reward.getId())
                .expiryDate(null)
                .build();

        pointTransactionRepository.save(pointTransaction);

        return RedeemResponse.builder()
                .redemptionCode("REWARD" + reward.getId())
                .pointUsed(reward.getRequiredPoints())
                .currentPoints(remainingPoints)
                .build();
    }
}