package com.group5.notificaion_service.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationKafkaListener {
    @KafkaListener(topics = "order-created", groupId = "notification-group")
    public void handleOrderCreated(String message) {

        log.info("Received event from Kafka: {}", message);

        // TODO
        // gửi email / push notification
    }
}
