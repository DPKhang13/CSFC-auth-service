package service;

import dto.request.RedeemRequest;
import dto.response.RedeemResponse;

public interface LoyaltyService {

    RedeemResponse redeemPoints(RedeemRequest redeemRequest);

}
