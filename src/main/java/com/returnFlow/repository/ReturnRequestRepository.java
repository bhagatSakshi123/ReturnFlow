package com.returnFlow.repository;

import com.returnFlow.entity.ReturnRequest;
import com.returnFlow.entity.User;
import com.returnFlow.entity.ReturnStatus;
import com.returnFlow.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {

    List<ReturnRequest> findByCustomer(User customer);

    List<ReturnRequest> findByStatus(ReturnStatus status);

    List<ReturnRequest> findByStatusIn(List<ReturnStatus> statuses);

    boolean existsByOrderItemAndStatusNot(
            OrderItem orderItem,
            ReturnStatus status
    );
}