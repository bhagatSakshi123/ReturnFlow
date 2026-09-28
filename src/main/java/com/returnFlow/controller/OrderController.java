package com.returnFlow.controller;

import com.returnFlow.entity.Order;
import com.returnFlow.entity.Role;
import com.returnFlow.entity.User;
import com.returnFlow.repository.UserRepository;
import com.returnFlow.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(
            OrderService orderService,
            UserRepository userRepository) {

        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Order> getAllOrders(
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        // SELLER and ADMIN can see all orders
        if (loggedInUser.getRole() == Role.SELLER ||
                loggedInUser.getRole() == Role.ADMIN) {

            return orderService.getAllOrders();
        }

        // CUSTOMER can see only their own orders
        return orderService.getCustomerOrders(loggedInUser);
    }

    @GetMapping("/{id}")
    public Order getOrderById(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        Order order = orderService.getOrderById(id);

        // CUSTOMER can access only their own order
        if (loggedInUser.getRole() == Role.CUSTOMER &&
                !order.getCustomer()
                        .getId()
                        .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You cannot view another customer's order"
            );
        }

        return order;
    }

    @PostMapping
    public Order createOrder(
            @RequestBody Order order) {

        return orderService.saveOrder(order);
    }

    private User getLoggedInUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Logged-in user not found"
                        ));
    }

    @PutMapping("/{id}/deliver")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<Order> markDelivered(
            @PathVariable Long id) {

        Order order = orderService.markDelivered(id);

        return ResponseEntity.ok(order);
    }
}