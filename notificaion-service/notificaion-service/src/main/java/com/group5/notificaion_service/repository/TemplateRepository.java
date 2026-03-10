package com.group5.notificaion_service.repository;

import com.group5.notificaion_service.model.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    // TODO: Thêm query methods
}
