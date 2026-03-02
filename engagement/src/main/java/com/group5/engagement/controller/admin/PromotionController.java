package com.group5.engagement.controller.admin;

import com.group5.engagement.dto.request.CreatePromotionRequest;
import com.group5.engagement.entity.Promotion;
import com.group5.engagement.service.PromotionService;
import com.group5.engagement.constants.PromotionStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@Valid @RequestBody CreatePromotionRequest request) {
        Promotion newPromotion = promotionService.createPromotion(request);
        return ResponseEntity.status(201).body(newPromotion);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Promotion>> getActivePromotions(
            @RequestParam(required = false) Long franchiseId) {
        List<Promotion> promotions = promotionService.getActivePromotions(franchiseId);
        return ResponseEntity.ok(promotions);
    }

    // GET /api/v1/promotions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        Promotion promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Promotion> updatePromotionStatus(
            @PathVariable Long id,
            @RequestParam PromotionStatus status) {
        Promotion promotion = promotionService.updatePromotionStatus(id, status);
        return ResponseEntity.ok(promotion);
    }

}