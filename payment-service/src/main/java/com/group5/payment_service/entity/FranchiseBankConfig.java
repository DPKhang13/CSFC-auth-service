package com.group5.payment_service.entity;

import com.group5.payment_service.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "franchise_bank_configs")
@Data
@EqualsAndHashCode(callSuper = true)
public class FranchiseBankConfig extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Long franchiseId; // ID từ Franchise Service

    @Column(nullable = false, length = 10)
    private String bankCode; // VD: "MB", "VCB", "TPB"

    @Column(nullable = false, length = 50)
    private String bankAccountNo; // Số tài khoản

    @Column(nullable = false)
    private String bankAccountName; // Tên chủ tài khoản (viết hoa không dấu)

    private String template; // Mẫu QR (compact, print...) - mặc định "compact2"

    @Column(name = "is_active")
    private boolean active = true;
}