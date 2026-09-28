package com.returnFlow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OrderItem orderItem;

    @ManyToOne
    private User customer;

    @Enumerated(EnumType.STRING)
    private ReturnReason reason;

    private String description;

    private String evidenceUrl;

    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status;

    private String rejectionReason;
}