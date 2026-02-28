package repository;

import entity.CustomerFranchies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFranchiesRepository extends JpaRepository<CustomerFranchies,Long> {
}
