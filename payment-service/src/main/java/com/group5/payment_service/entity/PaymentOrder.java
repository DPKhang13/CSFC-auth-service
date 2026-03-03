package com.group5.payment_service.entity;

import com.group5.payment_service.base.BaseEntity;
import com.group5.payment_service.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payment_orders", indexes = {
    @Index(name = "idx_transfer_content", columnList = "transfer_content"), // Index để tìm nhanh khi Webhook gọi
    @Index(name = "idx_order_ref", columnList = "ref_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentOrder extends BaseEntity {

    @Column(name = "ref_id", nullable = false)
    private String orderRefId; // Mã đơn hàng (VD: "ORD-123")

    @Column(nullable = false)
    private Long franchiseId; // Tiền này của ai?

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount; // Số tiền cần thu

    // KEY ĐỂ MATCHING: Chuỗi này sinh ngẫu nhiên (VD: "PAY8X9Z")
    // User bắt buộc phải điền cái này vào nội dung CK (QR tự điền)
    @Column(name = "transfer_content", nullable = false, unique = true)
    private String transferContent; 

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentStatus status;

    @Column(columnDefinition = "TEXT")
    private String qrUrl; // Link ảnh QR (cache lại dùng dần)

    private LocalDateTime expiredAt; // Hết hạn lúc nào?
    
    // Quan hệ 1-N: Một lệnh có thể có nhiều giao dịch (nếu khách ck lắt nhắt nhiều lần)
    @OneToMany(mappedBy = "paymentOrder", fetch = FetchType.LAZY)
    private List<PaymentTransaction> transactions;
}