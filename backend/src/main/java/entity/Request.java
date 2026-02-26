package entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column (name = "request_type")
    private String requestType;

    private String status;

    @Column (name = "requester_id")
    private Long requetsterId;

    @Column (name = "reject_reason")
    private String rejectReason;

    @Column (name = "handle_by")
    private Long handleBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
