package com.example.project_group5.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder

public class RedeemResponse {
    private String redemptionCode;
    private Integer pointUsed;
    private Integer currentPoints;

}
