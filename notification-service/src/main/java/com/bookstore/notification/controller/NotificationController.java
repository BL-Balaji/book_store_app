package com.bookstore.notification.controller;

import com.bookstore.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/notifications")
public class NotificationController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Notification Service is running", "OK"));
    }
}
