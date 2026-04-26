package com.tumipay.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Setter
@Getter
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "document_type", nullable = false, length = 10)
    private String documentType;
    @Column(name = "document_number", nullable = false, length = 30)
    private String documentNumber;
    @Column(name = "country_code", nullable = false, length = 5)
    private String countryCode;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "middle_name", length = 50)
    private String middleName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(name = "second_last_name", length = 50)
    private String secondLastName;

}
