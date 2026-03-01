package com.example.project_group5.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class LoyaltyRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private Long id;

    @Column(name = "loyalty_rule_name")
    private String name;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "point_multiplier")
    private Double pointMultiplier;

    @Column(name = "fixed_points")
    private Integer fixedPoints;

    @Column(name = "min_order_value")
    private Double minOrderValue;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endAt;

    @Column(name = "customer_franchies_id")
    private Long customerFranchiseId;

}
