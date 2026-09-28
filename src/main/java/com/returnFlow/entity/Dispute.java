package com.returnFlow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dispute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ReturnRequest returnRequest;

    @ManyToOne
    private User raisedBy;

    private String reason;

    private String description;

    @Enumerated(EnumType.STRING)
    private DisputeStatus status;

    private String resolution;
}