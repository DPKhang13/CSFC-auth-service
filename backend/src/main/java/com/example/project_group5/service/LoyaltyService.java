package com.example.project_group5.service;

import com.example.project_group5.dto.request.RedeemRequest;
import com.example.project_group5.dto.response.RedeemResponse;

public interface LoyaltyService {

    RedeemResponse redeemPoints(RedeemRequest redeemRequest);

}
