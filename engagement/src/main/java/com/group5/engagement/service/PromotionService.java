package com.group5.engagement.service;

import com.group5.engagement.dto.request.CreatePromotionRequest;
import com.group5.engagement.entity.Promotion;

public interface PromotionService {
    // Chỉ khai báo hàm, không viết code xử lý
    Promotion createPromotion(CreatePromotionRequest request);

}