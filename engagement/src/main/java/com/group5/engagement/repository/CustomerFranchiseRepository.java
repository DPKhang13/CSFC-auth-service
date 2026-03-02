package com.group5.engagement.repository;

import com.group5.engagement.entity.CustomerFranchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerFranchiseRepository extends JpaRepository<CustomerFranchise, Long> {

    // Tìm khách hàng theo User ID và Franchise ID (để check xem họ đã từng mua hàng ở đây chưa)
    Optional<CustomerFranchise> findByCustomerIdAndFranchiseId(Long customerId, Long franchiseId);

    // Kiểm tra nhanh xem khách hàng tồn tại chưa (trả về true/false)
    boolean existsByCustomerIdAndFranchiseId(Long customerId, Long franchiseId);
}