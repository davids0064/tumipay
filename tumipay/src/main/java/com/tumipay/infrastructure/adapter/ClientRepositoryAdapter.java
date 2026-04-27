package com.tumipay.infrastructure.adapter;

import com.tumipay.infrastructure.persistence.entity.ClientEntity;
import com.tumipay.domain.repository.ClientRepository;
import com.tumipay.infrastructure.persistence.repository.JpaClientRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepositoryAdapter implements ClientRepository {

    private final JpaClientRepository jpaClientRepository;

    public ClientRepositoryAdapter(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
    }

    @Override
    public ClientEntity save(ClientEntity client) {
        return jpaClientRepository.save(client);
    }

    @Override
    public Optional<ClientEntity> findById(Long clientId) {
        return jpaClientRepository.findById(clientId);
    }

}
