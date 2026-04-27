package com.tumipay.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Schema(name = "transaction_request", description = "Solicitud para crear una transacción")
public record TransactionRequest(
        @JsonProperty("client_transaction_id") @NotBlank String clientTransactionId,
        @JsonProperty("amount") @NotNull @Min(0) Long amount,
        @JsonProperty("currency") @NotBlank @Size(min = 3, max = 3) @Pattern(regexp = "^[A-Za-z]{3}$", message = "currency must be a 3-letter ISO 4217 code") String currency,
        @JsonProperty("country") @NotBlank @Size(min = 2, max = 2) @Pattern(regexp = "^[A-Za-z]{2}$", message = "country must be a 2-letter ISO 3166-1 alpha-2 code") String country,
        @JsonProperty("payment_method") @NotBlank String paymentMethod,
        @JsonProperty("webhook_url") @NotBlank @URL(message = "webhook_url must be a valid URL") String webhookUrl,
        @JsonProperty("redirect_url") @NotBlank @URL(message = "redirect_url must be a valid URL") String redirectUrl,
        @JsonProperty("description") String description,
        @JsonProperty("expiration") LocalDateTime expiration,
        @JsonProperty("client") @NotNull ClientInfo client
) { }
