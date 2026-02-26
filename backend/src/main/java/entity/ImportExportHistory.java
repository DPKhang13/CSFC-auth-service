package entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ImportExportHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ImportExportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request requestId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private items itemId;

    @Column(name = "action_type")
    private String actionType;

    private int quantity;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
