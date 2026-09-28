package com.returnFlow.repository;

import com.returnFlow.entity.Order;
import com.returnFlow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(User customer);
}