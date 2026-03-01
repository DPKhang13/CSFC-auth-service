package com.example.project_group5.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_transaction_id")
    private Long id;

    private Integer amount;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "loyalty_rule_id")
    private LoyaltyRule loyaltyRule;

    public enum actionType {
        EARN,
        REDEEM,
        EXPIRE
    }
}
