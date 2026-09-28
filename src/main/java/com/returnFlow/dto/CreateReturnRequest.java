package com.returnFlow.dto;

import com.returnFlow.entity.ReturnReason;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReturnRequest {

    private Long orderItemId;
    private ReturnReason reason;
    private String description;
    private String evidenceUrl;
}