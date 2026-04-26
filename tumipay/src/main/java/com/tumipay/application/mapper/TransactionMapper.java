package com.tumipay.application.mapper;

import com.tumipay.application.dto.TransactionData;
import com.tumipay.application.dto.TransactionRequest;
import com.tumipay.domain.model.Transaction;
import com.tumipay.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    private final ClientMapper clientMapper;

    public TransactionMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    public Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getTransactionId(),
                entity.getClientTransactionId(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getCountry(),
                entity.getPaymentMethod(),
                entity.getWebhookUrl(),
                entity.getRedirectUrl(),
                entity.getDescription(),
                entity.getExpiration(),
                entity.getCreatedAt(),
                clientMapper.toDomain(entity.getClient())
        );
    }

    public TransactionEntity toEntity(Transaction domain) {
        TransactionEntity entity = new TransactionEntity();
        entity.setTransactionId(domain.getTransactionId());
        entity.setClientTransactionId(domain.getClientTransactionId());
        entity.setAmount(domain.getAmount());
        entity.setCurrency(domain.getCurrency());
        entity.setCountry(domain.getCountry());
        entity.setPaymentMethod(domain.getPaymentMethod());
        entity.setWebhookUrl(domain.getWebhookUrl());
        entity.setRedirectUrl(domain.getRedirectUrl());
        entity.setDescription(domain.getDescription());
        entity.setExpiration(domain.getExpiration());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setClient(clientMapper.toEntity(domain.getClient()));
        return entity;
    }

    public Transaction toDomain(TransactionRequest dto) {
        return new Transaction(
                null,
                dto.clientTransactionId(),
                dto.amount(),
                dto.currency(),
                dto.country(),
                dto.paymentMethod(),
                dto.webhookUrl(),
                dto.redirectUrl(),
                dto.description(),
                dto.expiration(),
                LocalDateTime.now(),
                clientMapper.toDomain(dto.client())
        );
    }

    public TransactionData toDto(Transaction domain) {
        return new TransactionData(
                domain.getTransactionId(),
                domain.getClientTransactionId(),
                domain.getPaymentMethod(),
                domain.getCurrency(),
                domain.getCountry(),
                domain.getDescription(),
                domain.getCreatedAt()
        );
    }

}
