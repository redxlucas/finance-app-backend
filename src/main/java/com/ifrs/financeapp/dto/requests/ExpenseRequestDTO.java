package com.ifrs.financeapp.dto.requests;

import java.math.BigDecimal;

import com.ifrs.financeapp.model.transaction.TransactionType;

public record ExpenseRequestDTO(
        BigDecimal amount,
        String description,
        TransactionType type,
        Long categoryId) {
    public ExpenseRequestDTO {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
    }
}
