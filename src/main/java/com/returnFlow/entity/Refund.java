package com.returnFlow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ReturnRequest returnRequest;

    private BigDecimal amount;

    private LocalDateTime refundDate;

    @Enumerated(EnumType.STRING)
    private RefundMethod method;

    @Enumerated(EnumType.STRING)
    private RefundStatus status;
}