package com.bookstore.product.service;

import com.bookstore.common.exception.BadRequestException;
import com.bookstore.common.exception.ResourceNotFoundException;
import com.bookstore.product.dto.ProductRequest;
import com.bookstore.product.dto.ProductResponse;
import com.bookstore.product.entity.Category;
import com.bookstore.product.entity.Product;
import com.bookstore.product.repository.CategoryRepository;
import com.bookstore.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest req) {
        if (productRepository.existsByIsbn(req.getIsbn()))
            throw new BadRequestException("Product with ISBN " + req.getIsbn() + " already exists");

        Category category = null;
        if (req.getCategoryId() != null)
            category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", req.getCategoryId()));

        Product product = Product.builder()
                .title(req.getTitle()).author(req.getAuthor()).isbn(req.getIsbn())
                .description(req.getDescription()).price(req.getPrice())
                .stockQuantity(req.getStockQuantity()).publisher(req.getPublisher())
                .publicationYear(req.getPublicationYear()).language(req.getLanguage())
                .pages(req.getPages()).imageUrl(req.getImageUrl())
                .category(category).active(true).averageRating(0.0).totalReviews(0)
                .build();

        return toResponse(productRepository.save(product));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        return toResponse(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id)));
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchByKeyword(keyword).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        if (req.getTitle() != null) product.setTitle(req.getTitle());
        if (req.getAuthor() != null) product.setAuthor(req.getAuthor());
        if (req.getPrice() != null) product.setPrice(req.getPrice());
        if (req.getStockQuantity() != null) product.setStockQuantity(req.getStockQuantity());
        if (req.getDescription() != null) product.setDescription(req.getDescription());
        return toResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setActive(false);
        productRepository.save(product);
    }

    public List<ProductResponse> getByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId()).title(p.getTitle()).author(p.getAuthor()).isbn(p.getIsbn())
                .description(p.getDescription()).price(p.getPrice()).stockQuantity(p.getStockQuantity())
                .publisher(p.getPublisher()).publicationYear(p.getPublicationYear())
                .language(p.getLanguage()).pages(p.getPages()).imageUrl(p.getImageUrl())
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : null)
                .active(p.getActive()).averageRating(p.getAverageRating())
                .totalReviews(p.getTotalReviews()).createdAt(p.getCreatedAt())
                .build();
    }
}
