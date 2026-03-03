package com.group5.payment_service.entity;

import com.group5.payment_service.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "payment_webhook_logs")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentWebhookLog extends BaseEntity {

    private String source; // "CASSO", "SEPAY", "VIETQR"

    @Column(columnDefinition = "TEXT") // Lưu JSON dài
    private String rawData; 

    private String errorMessage; // Lưu lỗi nếu xử lý thất bại (VD: "Cannot match order")
    
    private int httpStatus; // 200, 400, 500
}