package com.bookstore.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Cart {
    private String userId;
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();
    private BigDecimal totalAmount;

    public BigDecimal calculateTotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
