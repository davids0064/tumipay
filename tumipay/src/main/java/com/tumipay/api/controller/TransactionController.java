package com.tumipay.api.controller;

import com.tumipay.application.dto.TransactionRequest;
import com.tumipay.application.dto.TransactionResponse;
import com.tumipay.application.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Transactions", description = "Operaciones para crear y consultar transacciones")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Crear transacción", description = "Crea una nueva transacción y la persiste en el repositorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transacción creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionRequest.class)), required = true) TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener transacción por ID", description = "Consulta una transacción por su `transaction_id` asignado por el microservicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{transaction_id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @Parameter(description = "UUID de la transacción generada por el servicio", required = true)
            @PathVariable("transaction_id") UUID transactionId) {
        TransactionResponse response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener transacción por client_transaction_id", description = "Consulta una transacción usando el identificador del cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/client/{client_transaction_id}")
    public ResponseEntity<TransactionResponse> getTransactionByClientTransactionId(
            @Parameter(description = "Identificador único de la transacción en el sistema cliente", required = true)
            @PathVariable("client_transaction_id") String clientTransactionId) {
        TransactionResponse response = transactionService.getTransactionByClientTransactionId(clientTransactionId);
        return ResponseEntity.ok(response);
    }

}
