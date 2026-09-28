package com.returnFlow.service;

import com.returnFlow.entity.Order;
import com.returnFlow.entity.OrderItem;
import com.returnFlow.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found"));
    }

    public List<OrderItem> getItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}