package com.ifrs.financeapp.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.financeapp.dto.TransactionRequestDTO;
import com.ifrs.financeapp.dto.TransactionResponseDTO;
import com.ifrs.financeapp.model.user.User;
import com.ifrs.financeapp.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transactionDTO,
            @AuthenticationPrincipal User user) {

        TransactionResponseDTO savedTransaction = transactionService.save(transactionDTO, user);
        return ResponseEntity.status(201).body(savedTransaction);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<TransactionResponseDTO>
    // getTransactionById(@PathVariable Long id) {
    // TransactionResponseDTO transaction = transactionService.getById(id);
    // return ResponseEntity.ok().body(transaction);
    // }

    @GetMapping("/all")
    public ResponseEntity<Page<TransactionResponseDTO>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User user) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(transactionService.getAll(pageable, user));
    }

    @GetMapping("/total/category/{category}")
    public ResponseEntity<BigDecimal> getTotalAmountByCategory(@PathVariable String category) {
        BigDecimal totalTransactions = transactionService.getTotalAmountByCategory(category);
        return ResponseEntity.ok().body(totalTransactions);
    }
}
