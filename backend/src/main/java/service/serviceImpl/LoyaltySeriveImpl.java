package service.serviceImpl;

import entity.PointTransaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.CustomerFranchiesRepository;
import service.LoyaltyService;

@Service
@RequiredArgsConstructor
public class LoyaltySeriveImpl implements LoyaltyService {

    private final CustomerFranchiesRepository customerFranchiesRepository;
    private final PointTransaction loyaltyTransaction;

    @Transactional

}
