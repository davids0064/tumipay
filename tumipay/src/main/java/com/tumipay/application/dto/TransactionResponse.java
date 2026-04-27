package com.tumipay.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "transaction_response", description = "Respuesta estándar para operaciones de transacciones")
public record TransactionResponse(
        @JsonProperty("code") String code,
        @JsonProperty("message") String message,
        @JsonProperty("transaction") TransactionData transaction
) {
}
