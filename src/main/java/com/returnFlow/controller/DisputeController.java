package com.returnFlow.controller;

import com.returnFlow.entity.Dispute;
import com.returnFlow.entity.DisputeStatus;
import com.returnFlow.service.DisputeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disputes")
public class DisputeController {

    private final DisputeService disputeService;

    public DisputeController(DisputeService disputeService) {
        this.disputeService = disputeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public Dispute createDispute(
            @RequestBody Dispute dispute) {

        return disputeService.createDispute(dispute);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public List<Dispute> getAllDisputes() {

        return disputeService.getAllDisputes();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public Dispute updateStatus(
            @PathVariable Long id,
            @RequestParam DisputeStatus status) {

        return disputeService.updateStatus(id, status);
    }
}