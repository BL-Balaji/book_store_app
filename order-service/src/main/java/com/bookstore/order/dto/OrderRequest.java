package com.bookstore.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderRequest {
    private String shippingAddress;
    private String paymentMethod;
    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequest> items;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class OrderItemRequest {
        private Long productId;
        private String productTitle;
        private Integer quantity;
        private java.math.BigDecimal unitPrice;
    }
}
