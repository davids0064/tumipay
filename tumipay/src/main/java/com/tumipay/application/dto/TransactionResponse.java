package com.tumipay.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionResponse(
        @JsonProperty("code") String code,
        @JsonProperty("message") String message,
        @JsonProperty("transaction") TransactionData transaction
) {
}
