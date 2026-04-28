package com.bookstore.wishlist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity @Table(name = "wishlist_items",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WishlistItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    private String productTitle;
    private String productAuthor;
    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime addedAt;
}
