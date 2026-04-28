package com.bookstore.wishlist.controller;

import com.bookstore.common.dto.ApiResponse;
import com.bookstore.wishlist.entity.WishlistItem;
import com.bookstore.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist", description = "Wishlist management APIs")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    @Operation(summary = "Add item to wishlist")
    public ResponseEntity<ApiResponse<WishlistItem>> add(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, Object> body) {
        Long productId = Long.valueOf(body.get("productId").toString());
        String title  = (String) body.getOrDefault("productTitle", "");
        String author = (String) body.getOrDefault("productAuthor", "");
        return ResponseEntity.ok(ApiResponse.success(
                wishlistService.addToWishlist(userId, productId, title, author), "Added to wishlist"));
    }

    @GetMapping
    @Operation(summary = "Get user wishlist")
    public ResponseEntity<ApiResponse<List<WishlistItem>>> get(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(ApiResponse.success(wishlistService.getWishlist(userId), "Wishlist retrieved"));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Remove item from wishlist")
    public ResponseEntity<ApiResponse<Void>> remove(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long productId) {
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok(ApiResponse.success(null, "Removed from wishlist"));
    }

    @DeleteMapping
    @Operation(summary = "Clear wishlist")
    public ResponseEntity<ApiResponse<Void>> clear(
            @RequestHeader("X-User-Id") String userId) {
        wishlistService.clearWishlist(userId);
        return ResponseEntity.ok(ApiResponse.success(null, "Wishlist cleared"));
    }
}
