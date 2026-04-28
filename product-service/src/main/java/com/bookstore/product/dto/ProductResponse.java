package com.bookstore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String publisher;
    private Integer publicationYear;
    private String language;
    private Integer pages;
    private String imageUrl;
    private String categoryName;
    private Boolean active;
    private Double averageRating;
    private Integer totalReviews;
    private LocalDateTime createdAt;
}
