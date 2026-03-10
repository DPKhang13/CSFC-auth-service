package com.group5.notificaion_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: Thêm các field: type, recipient, status, errorMessage, sentAt, createdAt
}
