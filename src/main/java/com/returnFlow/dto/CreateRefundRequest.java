package com.returnFlow.dto;

import com.returnFlow.entity.RefundMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRefundRequest {

    private Long returnId;
    private BigDecimal amount;
    private RefundMethod method;
}