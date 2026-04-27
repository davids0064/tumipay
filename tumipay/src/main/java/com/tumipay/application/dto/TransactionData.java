package com.tumipay.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "transaction_data", description = "Datos de la transacción devueltos por la API")
public record TransactionData(
        @JsonProperty("transaction_id") UUID transactionId,
        @JsonProperty("client_transaction_id") String clientTransactionId,
        @JsonProperty("payment_method") String paymentMethod,
        @JsonProperty("currency") String currency,
        @JsonProperty("country") String country,
        @JsonProperty("description") String description,
        @JsonProperty("created_at") LocalDateTime createdAt
) {
}
