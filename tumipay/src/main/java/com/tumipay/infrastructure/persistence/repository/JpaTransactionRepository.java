package com.tumipay.infrastructure.persistence.repository;

import com.tumipay.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    Optional<TransactionEntity> findByClientTransactionId(String clientTransactionId);
}
