package entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long id;

    @Column(name = "tier_name")
    private String name;

    @Column(name = "min_points")
    private Integer minPoints;

    @Column(columnDefinition = "TEXT")
    private Integer benefits;
}
