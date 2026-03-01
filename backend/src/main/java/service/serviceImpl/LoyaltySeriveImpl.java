//package service.serviceImpl;
//
//import dto.request.RedeemRequest;
//import dto.response.RedeemResponse;
//import entity.CustomerFranchise;
//import entity.Redemption;
//import entity.Reward;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import repository.CustomerFranchiesRepository;
//import repository.PointTransactionRepository;
//import repository.RedemRepository;
//import repository.RewardRepository;
//import service.LoyaltyService;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class LoyaltySeriveImpl implements LoyaltyService {
//
//    private final CustomerFranchiesRepository customerFranchiesRepository;
//    private final PointTransactionRepository pointTransactionRepository;
//    private final RedemRepository redemRepository;
//    private final RewardRepository rewardRepository;
//
//    @Override
//    public RedeemResponse redeemPoints(RedeemRequest request){
////        //lấy thông tin customer
////        CustomerFranchise customerFranchise = customerFranchiesRepository.findById(request.getCustromerFranchiesId())
////                .orElseThrow(() -> new RuntimeException("Customer not found"));
////
////        //lấy thông tin reward
////        Reward reward = rewardRepository.findById(request.getRewardId())
////                .orElseThrow(() -> new RuntimeException("Reward not found"));
////
////        //check reward còn đủ không
////        if(!Boolean.TRUE.equals(reward.getIsActive())){
////            throw new RuntimeException("Reward is not active");
////        }
////
////        //check điểm có đủ không
////        if(customerFranchise.getCurrentPoints() < reward.getPointsRequired()){
////            throw new RuntimeException("Not enough points to redeem this reward");
////        }
////
////        //trừ điểm
////        customerFranchise.setCurrentPoints(customerFranchise.getCurrentPoints() - reward.getPointsRequired());
////        customerFranchiesRepository.save(customerFranchise);
////
////        //create redemption record
////        Redemption redemption = new Redemption();
//////        redemption.setCustomerFranchise(customerFranchise.getCustomer());
////
////        return new RedeemResponse(
////                redemption.getRedemptionCode(),
////                reward.getPointsRequired(),
////                customerFranchise.getCurrentPoints()
////        );
//    }
//
//}
