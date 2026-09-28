package com.returnFlow.controller;

import com.returnFlow.entity.Order;
import com.returnFlow.entity.OrderItem;
import com.returnFlow.entity.Role;
import com.returnFlow.entity.User;
import com.returnFlow.repository.UserRepository;
import com.returnFlow.service.OrderItemService;
import com.returnFlow.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderItemController(
            OrderItemService orderItemService,
            OrderService orderService,
            UserRepository userRepository) {

        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems(
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        // SELLER and ADMIN can see all order items
        if (loggedInUser.getRole() == Role.SELLER ||
                loggedInUser.getRole() == Role.ADMIN) {

            return orderItemService.getAllOrderItems();
        }

        // CUSTOMER can see only their own order items
        return orderItemService.getAllOrderItems()
                .stream()
                .filter(item ->
                        item.getOrder()
                                .getCustomer()
                                .getId()
                                .equals(loggedInUser.getId()))
                .toList();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        OrderItem orderItem =
                orderItemService.getOrderItemById(id);

        // CUSTOMER can access only their own order item
        if (loggedInUser.getRole() == Role.CUSTOMER &&
                !orderItem.getOrder()
                        .getCustomer()
                        .getId()
                        .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You cannot view another customer's order item"
            );
        }

        return orderItem;
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItem> getItemsByOrder(
            @PathVariable Long orderId,
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        Order order = orderService.getOrderById(orderId);

        // CUSTOMER can access only their own order
        if (loggedInUser.getRole() == Role.CUSTOMER &&
                !order.getCustomer()
                        .getId()
                        .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You cannot view another customer's order items"
            );
        }

        return orderItemService.getItemsByOrder(order);
    }

    @PostMapping
    public OrderItem createOrderItem(
            @RequestBody OrderItem orderItem) {

        return orderItemService.saveOrderItem(orderItem);
    }

    private User getLoggedInUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Logged-in user not found"
                        ));
    }
}