package com.tumipay.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Client {

    private Long clientId;
    private String documentType;
    private String documentNumber;
    private String countryCode;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;

    public Client(Long clientId, String documentType, String documentNumber,
                  String countryCode, String phoneNumber, String email,
                  String firstName, String middleName, String lastName,
                  String secondLastName) {
        this.clientId = clientId;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
    }

}
