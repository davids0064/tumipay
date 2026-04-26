package com.tumipay.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.tumipay.tumipay.domain.port.in.PaymentUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para PaymentController.
 */
public class PaymentControllerTest {

    @Mock
    private PaymentUseCase paymentUseCase;

    @InjectMocks
    private PaymentController paymentController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCrearTransaccion() throws Exception {
        RequestDTO.ClienteInfo clienteInfo = new ClienteInfo(
                "DNI", "12345678", "+1", "5551234567", "cliente@ejemplo.com", "Juan", "Carlos", "Pérez", "Gómez"
        );
        RequestDTO requestDTO = new RequestDTO(
                "abc123", 1500, "USD", "US", "mp_001", "https://webhook.url", "https://redirect.url", clienteInfo, "Pago de servicio", LocalDateTime.now().plusDays(1)
        );

        Payment payment = new Payment();
        payment.setId("uuid-generated");

        when(paymentUseCase.procesarPago(any(Payment.class))).thenReturn(payment);

        ResponseEntity<ResponseDTO> response = paymentController.crearTransaccion(requestDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("000", response.getBody().codigoRespuestaProceso());
        verify(paymentUseCase, times(1)).procesarPago(any(Payment.class));
    }

    @Test
    void testConsultarTransaccion_Existente() {
        Payment payment = new Payment();
        payment.setId("abc123");
        payment.setFechaProcesamiento(LocalDateTime.now());

        when(paymentUseCase.consultarPagoPorId("abc123")).thenReturn(Optional.of(payment));

        ResponseEntity<ResponseDTO> response = paymentController.consultarTransaccion("abc123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("000", response.getBody().codigoRespuestaProceso());
        verify(paymentUseCase, times(1)).consultarPagoPorId("abc123");
    }

    @Test
    void testConsultarTransaccion_NoExistente() {
        when(paymentUseCase.consultarPagoPorId("nonexistent")).thenReturn(Optional.empty());

        ResponseEntity<ResponseDTO> response = paymentController.consultarTransaccion("nonexistent");

        assertEquals(404, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("001", response.getBody().codigoRespuestaProceso());
        verify(paymentUseCase, times(1)).consultarPagoPorId("nonexistent");
    }

}
// ...existing code...

