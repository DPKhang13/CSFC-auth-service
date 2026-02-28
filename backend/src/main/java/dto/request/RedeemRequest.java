package dto.request;

import lombok.Data;

@Data
public class RedeemRequest {

    private Long franchiesId;
    private ReedeemType redeemType;
    private Long orderId;
    private Integer points;
    private Long rewardId;
    private Long promotionId;

    public enum ReedeemType {
        ORDER_DISCOUNT,
        REWARD,
        COUPON
    }
}
