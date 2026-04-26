package com.tumipay.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class Transaction {

    private UUID transactionId;
    private String clientTransactionId;
    private Long amount;
    private String currency;
    private String country;
    private String paymentMethod;
    private String webhookUrl;
    private String redirectUrl;
    private String description;
    private LocalDateTime expiration;
    private LocalDateTime createdAt;
    private Client client;

    public Transaction(UUID transactionId, String clientTransactionId, Long amount,
                       String currency, String country, String paymentMethod,
                       String webhookUrl, String redirectUrl, String description,
                       LocalDateTime expiration, LocalDateTime createdAt,
                       Client client) {
        this.transactionId = transactionId;
        this.clientTransactionId = clientTransactionId;
        this.amount = amount;
        this.currency = currency;
        this.country = country;
        this.paymentMethod = paymentMethod;
        this.webhookUrl = webhookUrl;
        this.redirectUrl = redirectUrl;
        this.description = description;
        this.expiration = expiration;
        this.createdAt = createdAt;
        this.client = client;
    }

}
