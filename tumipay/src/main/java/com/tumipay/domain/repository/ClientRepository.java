package com.tumipay.domain.repository;

import com.tumipay.infrastructure.persistence.entity.ClientEntity;

import java.util.Optional;

public interface ClientRepository {
    ClientEntity save(ClientEntity client);
    Optional<ClientEntity> findById(Long clientId);
}
