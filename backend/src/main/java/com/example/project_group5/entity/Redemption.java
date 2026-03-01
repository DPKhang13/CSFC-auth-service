package com.example.project_group5.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "redemption")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Redemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "redemption_id")
    private Long id;

    @Column(name = "point_used")
    private Integer pointUsed;

    private String status;

    @Column(name = "redemption_code")
    private String redemptionCode;

    @Column(name = "redeemed_at")
    private LocalDateTime redeemedAt;

    @ManyToOne
    @JoinColumn(name = "customer_franchies_id")
    private CustomerFranchise customerFranchise;

    @ManyToOne
    @JoinColumn(name = "reward_id")
    private Reward reward;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

}
