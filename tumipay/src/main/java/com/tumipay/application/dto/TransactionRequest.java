package com.tumipay.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TransactionRequest(
        @JsonProperty("client_transaction_id") String clientTransactionId,
        @JsonProperty("amount") Long amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("country") String country,
        @JsonProperty("payment_method") String paymentMethod,
        @JsonProperty("webhook_url") String webhookUrl,
        @JsonProperty("redirect_url") String redirectUrl,
        @JsonProperty("description") String description,
        @JsonProperty("expiration") LocalDateTime expiration,
        @JsonProperty("client") ClientInfo client
) { }
