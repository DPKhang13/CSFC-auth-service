package com.example.project_group5.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "reward")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long id;

    @Column(name = "reward_name")
    private String name;

    @Column(name = "points_required")
    private Integer pointsRequired;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "franchise_id")
    private Long franchiseId;

}
