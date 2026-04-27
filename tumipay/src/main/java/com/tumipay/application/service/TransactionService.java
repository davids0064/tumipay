package com.tumipay.application.service;

import com.tumipay.application.dto.TransactionData;
import com.tumipay.application.dto.TransactionRequest;
import com.tumipay.application.dto.TransactionResponse;
import com.tumipay.application.mapper.ClientMapper;
import com.tumipay.application.mapper.TransactionMapper;
import com.tumipay.domain.model.Client;
import com.tumipay.domain.model.Transaction;
import com.tumipay.domain.repository.ClientRepository;
import com.tumipay.domain.repository.TransactionRepository;
import com.tumipay.infrastructure.persistence.entity.ClientEntity;
import com.tumipay.infrastructure.persistence.entity.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    private final TransactionMapper transactionMapper;
    private final ClientMapper clientMapper;

    /**
     * Crear una nueva transacción
     */
    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction transactionDomain = transactionMapper.toDomain(request);

        Client clientDomain = transactionDomain.getClient();
        ClientEntity clientEntity = clientMapper.toEntity(clientDomain);
        ClientEntity savedClient = clientRepository.save(clientEntity);

        transactionDomain = new Transaction(
                transactionDomain.getTransactionId(),
                transactionDomain.getClientTransactionId(),
                transactionDomain.getAmount(),
                transactionDomain.getCurrency(),
                transactionDomain.getCountry(),
                transactionDomain.getPaymentMethod(),
                transactionDomain.getWebhookUrl(),
                transactionDomain.getRedirectUrl(),
                transactionDomain.getDescription(),
                transactionDomain.getExpiration(),
                transactionDomain.getCreatedAt(),
                clientMapper.toDomain(savedClient)
        );

        TransactionEntity transactionEntity = transactionMapper.toEntity(transactionDomain);
        TransactionEntity savedTransaction = transactionRepository.save(transactionEntity);

        Transaction transactionSavedDomain = transactionMapper.toDomain(savedTransaction);
        TransactionData transactionData = transactionMapper.toDto(transactionSavedDomain);

        return new TransactionResponse(com.tumipay.application.error.ErrorCode.SUCCESS.getCode(), com.tumipay.application.error.ErrorCode.SUCCESS.getMessage(), transactionData);
    }

    /**
     * Consultar transacción por ID
     */
    public TransactionResponse getTransactionById(UUID transactionId) {
        TransactionEntity entity = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        Transaction domain = transactionMapper.toDomain(entity);
        TransactionData dto = transactionMapper.toDto(domain);

        return new TransactionResponse(com.tumipay.application.error.ErrorCode.SUCCESS.getCode(), com.tumipay.application.error.ErrorCode.SUCCESS.getMessage(), dto);
    }

    /**
     * Consultar transacción por client_transaction_id
     */
    public TransactionResponse getTransactionByClientTransactionId(String clientTransactionId) {
        TransactionEntity entity = transactionRepository.findByClientTransactionId(clientTransactionId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        Transaction domain = transactionMapper.toDomain(entity);
        TransactionData dto = transactionMapper.toDto(domain);

        return new TransactionResponse(com.tumipay.application.error.ErrorCode.SUCCESS.getCode(), com.tumipay.application.error.ErrorCode.SUCCESS.getMessage(), dto);
    }
}

