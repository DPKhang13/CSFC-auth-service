package entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    private String itemName;
    private String description;
    private String unit;

    @Column(name ="reorder_level")
    private Integer reorderLevel;

    @Column(name = "supplier_name")
    private String supplierName;

    private Integer quantity;
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_item_id")
    private CategoryItem categoryItemId;

}
