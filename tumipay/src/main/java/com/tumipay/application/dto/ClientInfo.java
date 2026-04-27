package com.tumipay.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;


@Schema(name = "client_info", description = "Información del cliente")
public record ClientInfo(
        @JsonProperty("document_type") @NotBlank String documentType,
        @JsonProperty("document_number") @NotBlank String documentNumber,
        @JsonProperty("country_code") @NotBlank @Pattern(regexp = "^[0-9+]{1,4}$", message = "country_code should be country calling code digits") String countryCode,
        @JsonProperty("phone_number") @NotBlank @Pattern(regexp = "^[0-9]{4,20}$", message = "phone_number should contain digits only, without country code") String phoneNumber,
        @JsonProperty("email") @NotBlank @Email String email,
        @JsonProperty("first_name") @NotBlank String firstName,
        @JsonProperty("middle_name") String middleName,
        @JsonProperty("last_name") @NotBlank String lastName,
        @JsonProperty("second_last_name") String secondLastName
) {
}
