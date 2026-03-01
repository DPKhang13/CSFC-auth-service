package entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @Column(name = "promotion_name")
    private String name;

    private String type;

    private String description;

    @Column(name = "point_required")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "franchies_id")
    private Franchise franchies;

}
