package entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_franchies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CustomerFranchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_franchies_id")
    private Long id;

    @Column(name = "current_points")
    private Integer currentPoints;

    @Column(name = "total_earned_points")
    private Integer totalEarnedPoints;

    @Column(name = "first_order_date")
    private LocalDateTime firstOrderDate;

    @Column(name = "last_order_date")
    private LocalDateTime lastOrderDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "franchies_id")
    private Franchise franchies;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;

}
