package com.returnFlow.service;

import com.returnFlow.entity.*;
import com.returnFlow.repository.ReturnInspectionRepository;
import com.returnFlow.repository.ReturnRequestRepository;
import com.returnFlow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InspectionService {

    private final ReturnInspectionRepository inspectionRepository;
    private final ReturnRequestRepository returnRequestRepository;
    private final UserRepository userRepository;

    public InspectionService(
            ReturnInspectionRepository inspectionRepository,
            ReturnRequestRepository returnRequestRepository,
            UserRepository userRepository) {

        this.inspectionRepository = inspectionRepository;
        this.returnRequestRepository = returnRequestRepository;
        this.userRepository = userRepository;
    }

    public ReturnInspection createInspection(
            Long returnId,
            Long inspectorId,
            InspectionCondition condition,
            InspectionDecision decision,
            String remarks) {

        ReturnRequest request = returnRequestRepository.findById(returnId)
                .orElseThrow(() -> new RuntimeException("Return not found"));

        User inspector = userRepository.findById(inspectorId)
                .orElseThrow(() -> new RuntimeException("Inspector not found"));

        if (request.getStatus() != ReturnStatus.RECEIVED) {
            throw new RuntimeException(
                    "Product must be received before inspection"
            );
        }

        ReturnInspection inspection = new ReturnInspection();

        inspection.setReturnRequest(request);
        inspection.setInspectedBy(inspector);
        inspection.setInspectionDate(LocalDateTime.now());
        inspection.setCondition(condition);
        inspection.setDecision(decision);
        inspection.setRemarks(remarks);

        request.setStatus(ReturnStatus.INSPECTION_COMPLETED);
        returnRequestRepository.save(request);

        return inspectionRepository.save(inspection);
    }
}