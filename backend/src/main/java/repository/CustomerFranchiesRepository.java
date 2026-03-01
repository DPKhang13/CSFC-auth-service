package repository;

import entity.CustomerFranchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFranchiesRepository extends JpaRepository<CustomerFranchise,Long> {
}
