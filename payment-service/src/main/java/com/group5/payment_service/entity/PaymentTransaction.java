package com.group5.payment_service.entity;

import com.group5.payment_service.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions", indexes = {
    @Index(name = "idx_bank_txn", columnList = "bank_transaction_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_order_id")
    private PaymentOrder paymentOrder; // Nullable: Nếu tiền vào mà không tìm thấy Order thì vẫn lưu transaction nhưng để null

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount; // Số tiền thực nhận vào tài khoản

    // Mã tham chiếu ngân hàng (FTxxxxxxxx). Unique để tránh cộng tiền 2 lần
    @Column(name = "bank_transaction_id", nullable = false, unique = true)
    private String bankTransactionId; 

    @Column(nullable = false)
    private String description; // Nội dung CK thực tế (VD: "NGUYEN VAN A chuyen tien PAY8X9Z")

    private LocalDateTime transactionDate; // Thời gian tiền vào

    private String senderBankAccount; // STK người chuyển (nếu ngân hàng trả về)
    private String senderBankName;    // Ngân hàng người chuyển
}