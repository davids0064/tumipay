package com.tumipay.application.mapper;

import com.tumipay.application.dto.ClientInfo;
import com.tumipay.domain.model.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {

    @Test
    void clientDtoToDomainAndBack() {
        ClientInfo dto = new ClientInfo(
                "CC",
                "987654321",
                "57",
                "3007654321",
                "cliente@example.com",
                "Ana",
                null,
                "Gomez",
                null
        );

        ClientMapper mapper = new ClientMapper();
        Client domain = mapper.toDomain(dto);

        assertNotNull(domain);
        assertEquals(dto.email(), domain.getEmail());
        assertEquals(dto.documentNumber(), domain.getDocumentNumber());

        var entity = mapper.toEntity(domain);
        assertNotNull(entity);
        assertEquals(domain.getEmail(), entity.getEmail());
    }
}
