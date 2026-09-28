package com.returnFlow.service;

import com.returnFlow.entity.Dispute;
import com.returnFlow.entity.DisputeStatus;
import com.returnFlow.repository.DisputeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisputeService {

    private final DisputeRepository disputeRepository;

    public DisputeService(DisputeRepository disputeRepository) {
        this.disputeRepository = disputeRepository;
    }

    public Dispute createDispute(Dispute dispute) {
        dispute.setStatus(DisputeStatus.OPEN);
        return disputeRepository.save(dispute);
    }

    public List<Dispute> getAllDisputes() {
        return disputeRepository.findAll();
    }

    public Dispute updateStatus(Long id, DisputeStatus status) {

        Dispute dispute = disputeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispute not found"));

        dispute.setStatus(status);

        return disputeRepository.save(dispute);
    }
}