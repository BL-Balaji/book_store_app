package com.bookstore.order.controller;

import com.bookstore.common.dto.ApiResponse;
import com.bookstore.order.dto.OrderRequest;
import com.bookstore.order.entity.Order;
import com.bookstore.order.entity.OrderStatus;
import com.bookstore.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Order processing APIs")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place a new order")
    public ResponseEntity<ApiResponse<Order>> placeOrder(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody OrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(orderService.placeOrder(userId, req), "Order placed successfully"));
    }

    @GetMapping
    @Operation(summary = "Get user orders")
    public ResponseEntity<ApiResponse<List<Order>>> getUserOrders(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getUserOrders(userId), "Orders retrieved"));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all orders (Admin)")
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(orderService.getAllOrders(), "All orders retrieved"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<ApiResponse<Order>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrderById(id), "Order retrieved"));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status (Admin)")
    public ResponseEntity<ApiResponse<Order>> updateStatus(
            @PathVariable Long id, @RequestBody Map<String, String> body) {
        OrderStatus status = OrderStatus.valueOf(body.get("status").toUpperCase());
        return ResponseEntity.ok(ApiResponse.success(orderService.updateStatus(id, status), "Status updated"));
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel order")
    public ResponseEntity<ApiResponse<Order>> cancel(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(ApiResponse.success(orderService.cancelOrder(id, userId), "Order cancelled"));
    }
}
