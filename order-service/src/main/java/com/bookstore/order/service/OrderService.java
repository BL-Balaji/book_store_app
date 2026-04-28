package com.bookstore.order.service;

import com.bookstore.common.exception.ResourceNotFoundException;
import com.bookstore.order.dto.OrderRequest;
import com.bookstore.order.entity.Order;
import com.bookstore.order.entity.OrderItem;
import com.bookstore.order.entity.OrderStatus;
import com.bookstore.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(String userId, OrderRequest req) {
        List<OrderItem> items = req.getItems().stream().map(i -> {
            BigDecimal subtotal = i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()));
            return OrderItem.builder()
                    .productId(i.getProductId()).productTitle(i.getProductTitle())
                    .quantity(i.getQuantity()).unitPrice(i.getUnitPrice()).subtotal(subtotal)
                    .build();
        }).collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(OrderItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .userId(userId).totalAmount(total)
                .shippingAddress(req.getShippingAddress())
                .paymentMethod(req.getPaymentMethod())
                .status(OrderStatus.PENDING)
                .build();

        items.forEach(i -> i.setOrder(order));
        order.setItems(items);

        Order saved = orderRepository.save(order);
        log.info("Order placed: id={}, user={}, total={}", saved.getId(), userId, total);
        // Kafka event would be published here in production
        return saved;
    }

    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long id, String userId) {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
