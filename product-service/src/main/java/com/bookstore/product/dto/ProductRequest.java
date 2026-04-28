package com.bookstore.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @NotBlank(message = "ISBN is required")
    private String isbn;
    private String description;
    @NotNull @DecimalMin("0.01")
    private BigDecimal price;
    @NotNull @Min(0)
    private Integer stockQuantity;
    private String publisher;
    private Integer publicationYear;
    private String language;
    private Integer pages;
    private String imageUrl;
    private Long categoryId;
}
