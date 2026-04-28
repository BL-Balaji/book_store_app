package com.bookstore.feedback.service;

import com.bookstore.common.exception.BadRequestException;
import com.bookstore.common.exception.ResourceNotFoundException;
import com.bookstore.feedback.entity.Review;
import com.bookstore.feedback.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review addReview(String userId, Long productId, Integer rating, String comment) {
        if (reviewRepository.existsByUserIdAndProductId(userId, productId))
            throw new BadRequestException("You have already reviewed this product");
        if (rating < 1 || rating > 5)
            throw new BadRequestException("Rating must be between 1 and 5");
        return reviewRepository.save(Review.builder()
                .userId(userId).productId(productId).rating(rating).comment(comment).build());
    }

    public List<Review> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getUserReviews(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Double getAverageRating(Long productId) {
        Double avg = reviewRepository.getAverageRatingByProductId(productId);
        return avg != null ? avg : 0.0;
    }

    @Transactional
    public Review updateReview(Long id, Integer rating, String comment) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        if (rating != null) review.setRating(rating);
        if (comment != null) review.setComment(comment);
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
