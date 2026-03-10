package com.group5.notificaion_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: Thêm các field: code, type, subject, template, isActive
}
