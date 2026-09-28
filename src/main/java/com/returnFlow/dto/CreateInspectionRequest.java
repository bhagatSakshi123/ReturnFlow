package com.returnFlow.dto;

import com.returnFlow.entity.InspectionCondition;
import com.returnFlow.entity.InspectionDecision;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInspectionRequest {

    private Long returnId;
    private Long inspectorId;
    private InspectionCondition condition;
    private InspectionDecision decision;
    private String remarks;
}