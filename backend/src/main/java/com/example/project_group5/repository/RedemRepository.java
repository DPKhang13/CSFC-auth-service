package com.example.project_group5.repository;

import com.example.project_group5.entity.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedemRepository extends JpaRepository<Redemption,Long> {
}
