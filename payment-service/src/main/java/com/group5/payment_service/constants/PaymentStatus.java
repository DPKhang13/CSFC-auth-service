package com.group5.payment_service.constants;

// File: com.example.payment.enums.PaymentStatus.java
public enum PaymentStatus {
    PENDING,    // Đang chờ thanh toán
    PAID,       // Đã thanh toán đủ
    PARTIAL,    // Khách chuyển thiếu tiền
    OVERPAID,   // Khách chuyển thừa tiền (xảy ra khá nhiều)
    CANCELLED   // Đã hủy
}