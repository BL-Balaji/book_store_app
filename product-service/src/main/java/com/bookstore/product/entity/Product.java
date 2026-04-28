package com.bookstore.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    private String publisher;
    private Integer publicationYear;
    private String language;
    private Integer pages;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    private Double averageRating = 0.0;

    @Builder.Default
    private Integer totalReviews = 0;

    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
