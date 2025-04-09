package com.example.project.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String method;
    private String status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private  Order order;
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
}
