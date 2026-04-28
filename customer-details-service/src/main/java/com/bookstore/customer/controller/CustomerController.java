package com.bookstore.customer.controller;

import com.bookstore.common.dto.ApiResponse;
import com.bookstore.customer.entity.CustomerAddress;
import com.bookstore.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Details", description = "Customer address management APIs")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/addresses")
    @Operation(summary = "Add a new address")
    public ResponseEntity<ApiResponse<CustomerAddress>> addAddress(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody CustomerAddress address) {
        address.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(customerService.addAddress(address), "Address added"));
    }

    @GetMapping("/addresses")
    @Operation(summary = "Get all addresses for user")
    public ResponseEntity<ApiResponse<List<CustomerAddress>>> getAddresses(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(ApiResponse.success(customerService.getAddresses(userId), "Addresses retrieved"));
    }

    @GetMapping("/addresses/{id}")
    @Operation(summary = "Get address by ID")
    public ResponseEntity<ApiResponse<CustomerAddress>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(customerService.getAddressById(id), "Address retrieved"));
    }

    @PutMapping("/addresses/{id}")
    @Operation(summary = "Update address")
    public ResponseEntity<ApiResponse<CustomerAddress>> update(
            @PathVariable Long id, @RequestBody CustomerAddress req) {
        return ResponseEntity.ok(ApiResponse.success(customerService.updateAddress(id, req), "Address updated"));
    }

    @DeleteMapping("/addresses/{id}")
    @Operation(summary = "Delete address")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        customerService.deleteAddress(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Address deleted"));
    }
}
