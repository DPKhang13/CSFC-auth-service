package com.group5.notificaion_service.repository;

import com.group5.notificaion_service.model.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    // TODO: Thêm query methods
}
