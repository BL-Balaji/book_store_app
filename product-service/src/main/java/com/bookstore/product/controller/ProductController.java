package com.bookstore.product.controller;

import com.bookstore.common.dto.ApiResponse;
import com.bookstore.product.dto.ProductRequest;
import com.bookstore.product.dto.ProductResponse;
import com.bookstore.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Book catalog APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product/book")
    public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(productService.createProduct(req), "Product created"));
    }

    @GetMapping
    @Operation(summary = "Get all active products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllProducts(), "Products retrieved"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<ApiResponse<ProductResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductById(id), "Product retrieved"));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by keyword")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success(productService.searchProducts(keyword), "Search results"));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.success(productService.getByCategory(categoryId), "Products by category"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<ApiResponse<ProductResponse>> update(@PathVariable Long id,
                                                               @RequestBody ProductRequest req) {
        return ResponseEntity.ok(ApiResponse.success(productService.updateProduct(id, req), "Product updated"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product (soft delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted"));
    }
}
