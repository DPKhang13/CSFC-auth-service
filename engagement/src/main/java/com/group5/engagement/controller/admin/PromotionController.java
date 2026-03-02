package com.group5.engagement.controller.admin;

import com.group5.engagement.dto.request.CreatePromotionRequest;
import com.group5.engagement.entity.Promotion;
import com.group5.engagement.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}