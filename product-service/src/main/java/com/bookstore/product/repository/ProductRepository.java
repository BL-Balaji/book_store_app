package com.bookstore.product.repository;

import com.bookstore.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    List<Product> findByActiveTrue();
    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);
    @Query("SELECT p FROM Product p WHERE p.active = true AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
           "LOWER(p.author) LIKE LOWER(CONCAT('%',:keyword,'%')))")
    List<Product> searchByKeyword(String keyword);
}
