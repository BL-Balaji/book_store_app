package com.bookstore.feedback.controller;

import com.bookstore.common.dto.ApiResponse;
import com.bookstore.feedback.entity.Review;
import com.bookstore.feedback.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Feedback & Reviews", description = "Product review and rating APIs")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Add a product review")
    public ResponseEntity<ApiResponse<Review>> addReview(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, Object> body) {
        Long productId = Long.valueOf(body.get("productId").toString());
        Integer rating = Integer.valueOf(body.get("rating").toString());
        String comment = (String) body.getOrDefault("comment", "");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(reviewService.addReview(userId, productId, rating, comment), "Review added"));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get all reviews for a product")
    public ResponseEntity<ApiResponse<List<Review>>> getProductReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getProductReviews(productId), "Reviews retrieved"));
    }

    @GetMapping("/product/{productId}/rating")
    @Operation(summary = "Get average rating for a product")
    public ResponseEntity<ApiResponse<Double>> getAverageRating(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getAverageRating(productId), "Average rating"));
    }

    @GetMapping("/user")
    @Operation(summary = "Get reviews by current user")
    public ResponseEntity<ApiResponse<List<Review>>> getUserReviews(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getUserReviews(userId), "User reviews"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a review")
    public ResponseEntity<ApiResponse<Review>> updateReview(
            @PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer rating = body.containsKey("rating") ? Integer.valueOf(body.get("rating").toString()) : null;
        String comment = (String) body.get("comment");
        return ResponseEntity.ok(ApiResponse.success(reviewService.updateReview(id, rating, comment), "Review updated"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Review deleted"));
    }
}
