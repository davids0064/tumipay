package com.tumipay.infrastructure.adapter;

import com.tumipay.infrastructure.persistence.entity.ClientEntity;
import com.tumipay.domain.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientRepository clientRepository;

    public ClientRepositoryAdapter(ClientRepository jpaClientRepository) {
        this.clientRepository = jpaClientRepository;
    }

    @Override
    public ClientEntity save(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<ClientEntity> findById(Long clientId) {
        return clientRepository.findById(clientId);
    }

}
