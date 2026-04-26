package com.tumipay.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class TransactionEntity {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private UUID transactionId;
    @Column(name = "client_transaction_id", nullable = false, length = 50)
    private String clientTransactionId;
    @Column(name = "amount", nullable = false)
    private Long amount;
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @Column(name = "country", nullable = false, length = 2)
    private String country;
    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;
    @Column(name = "webhook_url", nullable = false, length = 255)
    private String webhookUrl;
    @Column(name = "redirect_url", nullable = false, length = 255)
    private String redirectUrl;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "expiration")
    private LocalDateTime expiration;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
