package com.returnFlow.repository;

import com.returnFlow.entity.ReturnInspection;
import com.returnFlow.entity.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReturnInspectionRepository extends JpaRepository<ReturnInspection, Long> {

    Optional<ReturnInspection> findByReturnRequest(ReturnRequest returnRequest);
}