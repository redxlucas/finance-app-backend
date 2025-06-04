package com.ifrs.financeapp.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ifrs.financeapp.dto.CategoryDTO;
import com.ifrs.financeapp.model.transaction.Expense;
import com.ifrs.financeapp.model.transaction.TransactionType;

public record ExpenseResponseDTO(
        Long id,
        BigDecimal amount,
        LocalDateTime createdAt,
        TransactionType type,
        String description,
        String userLogin,
        CategoryDTO category) {

    public ExpenseResponseDTO(Expense expense) {
        this(
                expense.getId(),
                expense.getAmount(),
                expense.getCreatedAt(),
                expense.getType(),
                expense.getDescription(),
                expense.getUser().getLogin(),
                new CategoryDTO(expense.getCategory()));
    }
}
