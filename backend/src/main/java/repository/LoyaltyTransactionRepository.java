package repository;

import entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyTransactionRepository extends JpaRepository<PointTransaction,Long> {

}
