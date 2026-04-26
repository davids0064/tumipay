package com.tumipay.domain.repository;

import com.tumipay.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository {
    TransactionEntity save(TransactionEntity transaction);
    Optional<TransactionEntity> findById(UUID transactionId);
    Optional<TransactionEntity> findByClientTransactionId(String clientTransactionId);
}