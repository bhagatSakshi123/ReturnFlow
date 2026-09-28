package com.returnFlow.controller;

import com.returnFlow.dto.CreateInspectionRequest;
import com.returnFlow.entity.ReturnInspection;
import com.returnFlow.service.InspectionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inspections")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(
            InspectionService inspectionService) {

        this.inspectionService = inspectionService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ResponseEntity<ReturnInspection> createInspection(
            @RequestBody CreateInspectionRequest request) {

        ReturnInspection inspection =
                inspectionService.createInspection(
                        request.getReturnId(),
                        request.getInspectorId(),
                        request.getCondition(),
                        request.getDecision(),
                        request.getRemarks()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inspection);
    }
}