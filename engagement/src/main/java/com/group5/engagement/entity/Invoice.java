package com.group5.engagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long id;

    private Integer totalAmount;

    private String status;

    @Column(name = "order_id")
    private Long orderId;

    public enum status {
        UNPAID,
        PAID,
        CANCELLED,
    }
}
