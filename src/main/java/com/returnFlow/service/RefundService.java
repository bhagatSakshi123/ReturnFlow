package com.returnFlow.service;

import com.returnFlow.entity.*;
import com.returnFlow.repository.RefundRepository;
import com.returnFlow.repository.ReturnRequestRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RefundService {

    private final RefundRepository refundRepository;
    private final ReturnRequestRepository returnRequestRepository;

    public RefundService(
            RefundRepository refundRepository,
            ReturnRequestRepository returnRequestRepository) {

        this.refundRepository = refundRepository;
        this.returnRequestRepository = returnRequestRepository;
    }

    public Refund createRefund(
            Long returnId,
            BigDecimal amount,
            RefundMethod method) {

        ReturnRequest request = returnRequestRepository.findById(returnId)
                .orElseThrow(() -> new RuntimeException("Return not found"));

        if (request.getStatus() != ReturnStatus.INSPECTION_COMPLETED) {
            throw new RuntimeException(
                    "Inspection must be completed before refund"
            );
        }

        Refund refund = new Refund();

        refund.setReturnRequest(request);
        refund.setAmount(amount);
        refund.setMethod(method);
        refund.setRefundDate(LocalDateTime.now());
        refund.setStatus(RefundStatus.INITIATED);

        request.setStatus(ReturnStatus.REFUND_INITIATED);

        returnRequestRepository.save(request);

        return refundRepository.save(refund);
    }

    // NEW
    public Refund getRefundByReturnId(Long returnId) {

        ReturnRequest request = returnRequestRepository.findById(returnId)
                .orElseThrow(() -> new RuntimeException("Return not found"));

        return refundRepository.findByReturnRequest(request)
                .orElseThrow(() -> new RuntimeException("Refund not found"));
    }

    public Refund completeRefund(Long refundId) {

        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found"));

        if (refund.getStatus() != RefundStatus.INITIATED) {
            throw new RuntimeException(
                    "Only initiated refunds can be completed"
            );
        }

        refund.setStatus(RefundStatus.COMPLETED);

        ReturnRequest request = refund.getReturnRequest();

        request.setStatus(ReturnStatus.REFUNDED);

        returnRequestRepository.save(request);

        return refundRepository.save(refund);
    }
}