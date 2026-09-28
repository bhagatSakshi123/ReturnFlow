package com.returnFlow.repository;

import com.returnFlow.entity.Refund;
import com.returnFlow.entity.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    Optional<Refund> findByReturnRequest(ReturnRequest returnRequest);
}