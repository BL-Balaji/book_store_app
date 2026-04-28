package com.bookstore.feedback.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity @Table(name = "reviews",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(nullable = false)
    private Integer rating; // 1-5
    @Column(length = 1000)
    private String comment;
    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
