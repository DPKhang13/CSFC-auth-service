package com.example.project_group5.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "tier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long id;

    @Column(name = "tier_name")
    private String name;

    @Column(name = "min_points")
    private Integer minPoints;

    @Column(name = "tier_multiplier")
    private Double multiplier;

    @Column(columnDefinition = "TEXT")
    private Integer benefits;

    @Column(name = "franchise_id")
    private Long franchiseId;

}
