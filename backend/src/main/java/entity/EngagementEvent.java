package entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "engagement_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class EngagementEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engagement_event_id")
    private Long id;

    @Column(name = "event_type")
    private String eventType;

    private Integer source;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(columnDefinition = "json")
    private String metadata;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
