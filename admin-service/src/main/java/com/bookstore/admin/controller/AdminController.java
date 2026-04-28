package com.bookstore.admin.controller;

import com.bookstore.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController @RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin dashboard and management APIs")
public class AdminController {

    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", 0);
        stats.put("totalProducts", 0);
        stats.put("totalOrders", 0);
        stats.put("totalRevenue", 0.0);
        stats.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(ApiResponse.success(stats, "Dashboard stats retrieved"));
    }

    @GetMapping("/health")
    @Operation(summary = "Admin service health check")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Admin Service is running", "OK"));
    }
}
