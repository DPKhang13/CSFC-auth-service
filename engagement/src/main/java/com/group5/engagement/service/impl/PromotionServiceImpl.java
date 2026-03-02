package com.group5.engagement.service.impl;

import com.group5.engagement.constants.PromotionStatus;
import com.group5.engagement.dto.request.CreatePromotionRequest;
import com.group5.engagement.entity.Promotion;
import com.group5.engagement.repository.PromotionRepository;
import com.group5.engagement.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Đánh dấu đây là Bean để Spring quản lý
@RequiredArgsConstructor // Tự động inject Repository qua Constructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public Promotion createPromotion(CreatePromotionRequest request) {
        // 1. Validate Business Rules
        
        // Rule: Start date phải trước End date
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        // Rule: Kiểm tra trùng thời gian (Overlapping)
        boolean isOverlapping = promotionRepository.existsOverlappingPromotion(
                request.getFranchiseId(),
                request.getStartDate(),
                request.getEndDate()
        );

        if (isOverlapping) {
            throw new IllegalStateException("A promotion already exists in this time range.");
        }

        // 2. Mapping DTO -> Entity
        Promotion promotion = new Promotion();
        promotion.setFranchiseId(request.getFranchiseId());
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());

        // Rule: Mới tạo thì để Draft
        promotion.setStatus(PromotionStatus.DRAFT);

        // 3. Lưu xuống DB
        return promotionRepository.save(promotion);
    }
}