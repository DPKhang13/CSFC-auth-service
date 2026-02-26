package entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Item_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "image_id")
    private Long imageId;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private items itemId;

}
