package com.tumipay.application.mapper;

import com.tumipay.application.dto.ClientInfo;
import com.tumipay.application.dto.TransactionData;
import com.tumipay.application.dto.TransactionRequest;
import com.tumipay.domain.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    @Test
    void toDomainAndToDto_basicMapping() {
        ClientInfo client = new ClientInfo(
                "CC",
                "12345678",
                "57",
                "3001234567",
                "test@example.com",
                "Juan",
                "Carlos",
                "Perez",
                "Lopez"
        );

        TransactionRequest req = new TransactionRequest(
                "cli-123",
                1000L,
                "COP",
                "CO",
                "CARD",
                "https://example.com/webhook",
                "https://example.com/redirect",
                "Pago prueba",
                LocalDateTime.now().plusDays(1),
                client
        );

        ClientMapper clientMapper = new ClientMapper();
        TransactionMapper mapper = new TransactionMapper(clientMapper);

        Transaction domain = mapper.toDomain(req);
        assertNotNull(domain);
        assertEquals(req.clientTransactionId(), domain.getClientTransactionId());
        assertEquals(req.amount(), domain.getAmount());
        assertEquals(req.currency(), domain.getCurrency());
        assertEquals(req.paymentMethod(), domain.getPaymentMethod());
        assertNotNull(domain.getClient());
        assertEquals(req.client().email(), domain.getClient().getEmail());

        TransactionData dto = mapper.toDto(domain);
        assertNotNull(dto);
        assertEquals(domain.getClientTransactionId(), dto.clientTransactionId());
        assertEquals(domain.getPaymentMethod(), dto.paymentMethod());
        assertEquals(domain.getCurrency(), dto.currency());
    }
}
