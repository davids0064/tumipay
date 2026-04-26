package com.tumipay.application.mapper;

import com.tumipay.application.dto.ClientInfo;
import com.tumipay.domain.model.Client;
import com.tumipay.infrastructure.persistence.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toDomain(ClientEntity entity) {
        return new Client(
                entity.getClientId(),
                entity.getDocumentType(),
                entity.getDocumentNumber(),
                entity.getCountryCode(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getLastName(),
                entity.getSecondLastName()
        );
    }

    public ClientEntity toEntity(Client domain) {
        ClientEntity entity = new ClientEntity();
        entity.setClientId(domain.getClientId());
        entity.setDocumentType(domain.getDocumentType());
        entity.setDocumentNumber(domain.getDocumentNumber());
        entity.setCountryCode(domain.getCountryCode());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setEmail(domain.getEmail());
        entity.setFirstName(domain.getFirstName());
        entity.setMiddleName(domain.getMiddleName());
        entity.setLastName(domain.getLastName());
        entity.setSecondLastName(domain.getSecondLastName());
        return entity;
    }

    public Client toDomain(ClientInfo dto) {
        return new Client(
                null,
                dto.documentType(),
                dto.documentNumber(),
                dto.countryCode(),
                dto.phoneNumber(),
                dto.email(),
                dto.firstName(),
                dto.middleName(),
                dto.lastName(),
                dto.secondLastName()
        );
    }

    public ClientInfo toDto(Client domain) {
        return new ClientInfo(
                domain.getDocumentType(),
                domain.getDocumentNumber(),
                domain.getCountryCode(),
                domain.getPhoneNumber(),
                domain.getEmail(),
                domain.getFirstName(),
                domain.getMiddleName(),
                domain.getLastName(),
                domain.getSecondLastName()
        );
    }
}