package com.bookstore.cart.controller;

import com.bookstore.cart.model.Cart;
import com.bookstore.cart.model.CartItem;
import com.bookstore.cart.service.CartService;
import com.bookstore.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController @RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Shopping cart management APIs")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get user cart")
    public ResponseEntity<ApiResponse<Cart>> getCart(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(userId), "Cart retrieved"));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<ApiResponse<Cart>> addItem(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, Object> body) {
        CartItem item = CartItem.builder()
                .productId(Long.valueOf(body.get("productId").toString()))
                .productTitle((String) body.getOrDefault("productTitle", ""))
                .productAuthor((String) body.getOrDefault("productAuthor", ""))
                .quantity(Integer.valueOf(body.get("quantity").toString()))
                .unitPrice(new BigDecimal(body.get("unitPrice").toString()))
                .build();
        return ResponseEntity.ok(ApiResponse.success(cartService.addItem(userId, item), "Item added to cart"));
    }

    @PutMapping("/items/{productId}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<ApiResponse<Cart>> updateItem(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long productId,
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(ApiResponse.success(
                cartService.updateItem(userId, productId, body.get("quantity")), "Cart updated"));
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<ApiResponse<Cart>> removeItem(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(cartService.removeItem(userId, productId), "Item removed"));
    }

    @DeleteMapping
    @Operation(summary = "Clear cart")
    public ResponseEntity<ApiResponse<Void>> clearCart(
            @RequestHeader("X-User-Id") String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success(null, "Cart cleared"));
    }
}
