package com.group5.notificaion_service.model.event;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private Long orderId;
    private String customerEmail;
    private Double totalPrice;

}
