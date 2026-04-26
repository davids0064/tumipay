package com.tumipay.api.controller;

import com.tumipay.application.dto.TransactionRequest;
import com.tumipay.application.dto.TransactionResponse;
import com.tumipay.application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Crear una nueva transacción
     */
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consultar transacción por ID
     */
    @GetMapping("/{transaction_id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable("transaction_id") UUID transactionId) {
        TransactionResponse response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }

    /**
     * Consultar transacción por client_transaction_id
     */
    @GetMapping("/client/{client_transaction_id}")
    public ResponseEntity<TransactionResponse> getTransactionByClientTransactionId(
            @PathVariable("client_transaction_id") String clientTransactionId) {
        TransactionResponse response = transactionService.getTransactionByClientTransactionId(clientTransactionId);
        return ResponseEntity.ok(response);
    }

}
