package com.tumipay.infrastructure.adapter;

import com.tumipay.infrastructure.persistence.entity.TransactionEntity;
import com.tumipay.infrastructure.persistence.repository.JpaTransactionRepository;
import com.tumipay.domain.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;

    public TransactionRepositoryAdapter(JpaTransactionRepository jpaTransactionRepository) {
        this.jpaTransactionRepository = jpaTransactionRepository;
    }

    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        return jpaTransactionRepository.save(transaction);
    }

    @Override
    public Optional<TransactionEntity> findById(UUID transactionId) {
        return jpaTransactionRepository.findById(transactionId);
    }

    @Override
    public Optional<TransactionEntity> findByClientTransactionId(String clientTransactionId) {
        return jpaTransactionRepository.findByClientTransactionId(clientTransactionId);
    }

}
