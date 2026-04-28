package com.bookstore.wishlist.service;

import com.bookstore.common.exception.BadRequestException;
import com.bookstore.common.exception.ResourceNotFoundException;
import com.bookstore.wishlist.entity.WishlistItem;
import com.bookstore.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Transactional
    public WishlistItem addToWishlist(String userId, Long productId, String title, String author) {
        if (wishlistRepository.existsByUserIdAndProductId(userId, productId))
            throw new BadRequestException("Product already in wishlist");
        return wishlistRepository.save(WishlistItem.builder()
                .userId(userId).productId(productId)
                .productTitle(title).productAuthor(author).build());
    }

    public List<WishlistItem> getWishlist(String userId) {
        return wishlistRepository.findByUserId(userId);
    }

    @Transactional
    public void removeFromWishlist(String userId, Long productId) {
        if (!wishlistRepository.existsByUserIdAndProductId(userId, productId))
            throw new ResourceNotFoundException("Wishlist item not found");
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Transactional
    public void clearWishlist(String userId) {
        wishlistRepository.deleteByUserId(userId);
    }
}
