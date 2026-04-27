package com.tumipay.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionE2EPostgresTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("tumipaydb")
            .withUsername("tumipay")
            .withPassword("tumipay123");

        private final ObjectMapper mapper = new ObjectMapper();

        @LocalServerPort
        private int port;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Test
    void createAndGetTransaction_postgres() throws Exception {
        String payload = "{\n" +
                "  \"client_transaction_id\": \"e2e-pg-1\",\n" +
                "  \"amount\": 3000,\n" +
                "  \"currency\": \"USD\",\n" +
                "  \"country\": \"US\",\n" +
                "  \"payment_method\": \"CARD\",\n" +
                "  \"webhook_url\": \"https://example.com/webhook\",\n" +
                "  \"redirect_url\": \"https://example.com/redirect\",\n" +
                "  \"description\": \"E2E Postgres test\",\n" +
                "  \"expiration\": null,\n" +
                "  \"client\": {\n" +
                "    \"document_type\": \"CC\",\n" +
                "    \"document_number\": \"444555666\",\n" +
                "    \"country_code\": \"57\",\n" +
                "    \"phone_number\": \"3007654321\",\n" +
                "    \"email\": \"e2epg@example.com\",\n" +
                "    \"first_name\": \"E2E\",\n" +
                "    \"middle_name\": null,\n" +
                "    \"last_name\": \"PGTester\",\n" +
                "    \"second_last_name\": null\n" +
                "  }\n" +
                "}";

        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
        var request = java.net.http.HttpRequest.newBuilder()
                .uri(new java.net.URI("http://localhost:" + port + "/api/v1/transactions"))
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(payload))
                .build();

        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        org.assertj.core.api.Assertions.assertThat(response.statusCode()).isEqualTo(201);
        String body = response.body();
        JsonNode root = mapper.readTree(body);
        String txId = root.path("transaction").path("transaction_id").asText();
        assertThat(txId).isNotBlank();

        var getReq = java.net.http.HttpRequest.newBuilder()
                .uri(new java.net.URI("http://localhost:" + port + "/api/v1/transactions/" + txId))
                .GET()
                .build();

        var getResp = client.send(getReq, java.net.http.HttpResponse.BodyHandlers.ofString());
        org.assertj.core.api.Assertions.assertThat(getResp.statusCode()).isEqualTo(200);
        JsonNode getRoot = mapper.readTree(getResp.body());
        org.assertj.core.api.Assertions.assertThat(getRoot.path("code").asText()).isEqualTo("000");
        org.assertj.core.api.Assertions.assertThat(getRoot.path("transaction").path("client_transaction_id").asText()).isEqualTo("e2e-pg-1");
    }
}
