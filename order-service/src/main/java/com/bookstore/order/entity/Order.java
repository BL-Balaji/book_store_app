package com.bookstore.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "orders")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;
    @Column(nullable = false)
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String paymentMethod;
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
