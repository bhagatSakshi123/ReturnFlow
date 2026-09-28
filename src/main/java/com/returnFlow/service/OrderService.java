package com.returnFlow.service;

import com.returnFlow.entity.Order;
import com.returnFlow.entity.OrderStatus;
import com.returnFlow.entity.User;
import com.returnFlow.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getCustomerOrders(User customer) {
        return orderRepository.findByCustomer(customer);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order markDelivered(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new RuntimeException(
                    "Only placed orders can be marked as delivered"
            );
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(LocalDateTime.now());

        return orderRepository.save(order);
    }
}