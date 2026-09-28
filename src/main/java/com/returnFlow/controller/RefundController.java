package com.returnFlow.controller;

import com.returnFlow.dto.CreateRefundRequest;
import com.returnFlow.entity.Refund;
import com.returnFlow.service.RefundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<Refund> createRefund(
            @RequestBody CreateRefundRequest request) {

        Refund refund = refundService.createRefund(
                request.getReturnId(),
                request.getAmount(),
                request.getMethod()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(refund);
    }

    // NEW
    @GetMapping("/return/{returnId}")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public Refund getRefundByReturnId(
            @PathVariable Long returnId) {

        return refundService.getRefundByReturnId(returnId);
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public Refund completeRefund(
            @PathVariable Long id) {

        return refundService.completeRefund(id);
    }
}