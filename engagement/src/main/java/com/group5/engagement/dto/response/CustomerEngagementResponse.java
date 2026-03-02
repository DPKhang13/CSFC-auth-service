package com.group5.engagement.dto.response;

import com.group5.engagement.constants.CustomerStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CustomerEngagementResponse {
    private Long id;
    private Long customerId;
    private Long franchiseId;
    private Integer currentPoints;
    private Integer totalEarnedPoints;
    private String tierName;
    private CustomerStatus status;
    private LocalDateTime firstOrderAt;
    private LocalDateTime lastOrderAt;
    private LocalDateTime createdAt;
}
