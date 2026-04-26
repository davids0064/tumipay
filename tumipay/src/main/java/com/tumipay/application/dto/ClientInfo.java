package com.tumipay.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientInfo(
        @JsonProperty("document_type") String documentType,
        @JsonProperty("document_number") String documentNumber,
        @JsonProperty("country_code") String countryCode,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("email") String email,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("middle_name") String middleName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("second_last_name") String secondLastName
) {
}
