package repository;

import entity.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedemRepository extends JpaRepository<Redemption,Long> {
}
