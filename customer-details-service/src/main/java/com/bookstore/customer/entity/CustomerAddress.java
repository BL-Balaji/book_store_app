package com.bookstore.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity @Table(name = "customer_addresses")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CustomerAddress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;
    private String fullName;
    private String phoneNumber;
    @Column(nullable = false)
    private String addressLine1;
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String zipCode;
    @Column(nullable = false)
    private String country;
    @Builder.Default
    private Boolean isDefault = false;
    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;
}
