package com.returnFlow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReturnInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ReturnRequest returnRequest;

    @ManyToOne
    private User inspectedBy;

    private LocalDateTime inspectionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_condition")
    private InspectionCondition condition;

    @Enumerated(EnumType.STRING)
    private InspectionDecision decision;

    private String remarks;
}