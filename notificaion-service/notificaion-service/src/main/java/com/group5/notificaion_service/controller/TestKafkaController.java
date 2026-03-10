package com.group5.notificaion_service.controller;

import com.group5.notificaion_service.model.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestKafkaController {
    
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    
    @PostMapping("/send-order-event")
    public String sendTestOrderEvent() {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
            .orderId(123L)
            .customerEmail("test@gmail.com")
            .totalPrice(100.0)
            .createdAt(LocalDateTime.now())
            .build();
        
        kafkaTemplate.send("order-created", event.getOrderId().toString(), event);
        log.info("✅ Test event sent to Kafka: {}", event);
        
        return "Event sent: " + event;
    }
}
