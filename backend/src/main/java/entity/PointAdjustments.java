package entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_adjustments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PointAdjustments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adjustment_id")
    private Long id;

    @Column(name = "change_amount")
    private Integer changeAmount;

    private String reason;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "loyalty_rule_id")
    private LoyaltyRule loyaltyRule;
}
