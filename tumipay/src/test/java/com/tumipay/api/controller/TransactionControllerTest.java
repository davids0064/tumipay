package com.tumipay.api.controller;

import com.tumipay.application.dto.TransactionData;
import com.tumipay.application.dto.TransactionResponse;
import com.tumipay.application.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest {

    private MockMvc mockMvc;
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        transactionService = Mockito.mock(TransactionService.class);
        TransactionController controller = new TransactionController(transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new com.tumipay.api.handler.ApiExceptionHandler())
                .build();
    }

    @Test
    void createAndGetTransaction_flow_unit() throws Exception {
        UUID txId = UUID.randomUUID();
        TransactionData data = new TransactionData(
                txId,
                "cli-9999",
                "CARD",
                "USD",
                "US",
                "Integration payment",
                LocalDateTime.now()
        );

        TransactionResponse createResp = new TransactionResponse("000", "Successful operation", data);
        Mockito.when(transactionService.createTransaction(Mockito.any())).thenReturn(createResp);
        Mockito.when(transactionService.getTransactionById(txId)).thenReturn(createResp);

        String payload = "{\n" +
                "  \"client_transaction_id\": \"cli-9999\",\n" +
                "  \"amount\": 1500,\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"country\": \"US\",\n" +
                "  \"payment_method\": \"CARD\",\n" +
                "  \"webhook_url\": \"https://example.com/webhook\",\n" +
                "  \"redirect_url\": \"https://example.com/redirect\",\n" +
                "  \"description\": \"Integration test payment\",\n" +
                "  \"expiration\": null,\n" +
                "  \"client\": {\n" +
                "    \"document_type\": \"CC\",\n" +
                "    \"document_number\": \"111222333\",\n" +
                "    \"country_code\": \"1\",\n" +
                "    \"phone_number\": \"5551234\",\n" +
                "    \"email\": \"inttest@example.com\",\n" +
                "    \"first_name\": \"Test\",\n" +
                "    \"middle_name\": null,\n" +
                "    \"last_name\": \"User\",\n" +
                "    \"second_last_name\": null\n" +
                "  }\n" +
                "}";

        var result = mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("000"))
                .andExpect(jsonPath("$.transaction.client_transaction_id").value("cli-9999"))
                .andReturn();

        mockMvc.perform(get("/api/v1/transactions/" + txId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("000"))
                .andExpect(jsonPath("$.transaction.client_transaction_id").value("cli-9999"));
    }
}
