package com.bookstore.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}
