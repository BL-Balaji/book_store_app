package com.bookstore.cart.service;

import com.bookstore.cart.model.Cart;
import com.bookstore.cart.model.CartItem;
import com.bookstore.common.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Cart Service using in-memory storage.
 * In production, this uses Redis for persistence.
 */
@Service @Slf4j
public class CartService {

    // In-memory store (replaces Redis for local testing)
    private final Map<String, Cart> cartStore = new HashMap<>();

    public Cart getCart(String userId) {
        return cartStore.computeIfAbsent(userId, id -> Cart.builder().userId(id).build());
    }

    public Cart addItem(String userId, CartItem item) {
        Cart cart = getCart(userId);
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(item.getProductId())).findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
            existing.get().setSubtotal(existing.get().getUnitPrice()
                    .multiply(BigDecimal.valueOf(existing.get().getQuantity())));
        } else {
            item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            cart.getItems().add(item);
        }
        cart.setTotalAmount(cart.calculateTotal());
        cartStore.put(userId, cart);
        return cart;
    }

    public Cart updateItem(String userId, Long productId, Integer quantity) {
        Cart cart = getCart(userId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
            item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        cart.setTotalAmount(cart.calculateTotal());
        cartStore.put(userId, cart);
        return cart;
    }

    public Cart removeItem(String userId, Long productId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        cart.setTotalAmount(cart.calculateTotal());
        cartStore.put(userId, cart);
        return cart;
    }

    public void clearCart(String userId) {
        cartStore.remove(userId);
    }
}
