package entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "franchies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Franchies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "franchies_id")
    private Long id;

    @Column(name = "franchies_name")
    private String name;

    @Column(name = "franchies_code")
    private String code;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
