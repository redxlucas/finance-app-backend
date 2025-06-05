package com.ifrs.financeapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ifrs.financeapp.model.transaction.CurrencyType;
import com.ifrs.financeapp.model.transaction.FixedRecurrencePeriodType;
import com.ifrs.financeapp.model.transaction.RecurrenceType;
import com.ifrs.financeapp.model.transaction.Transaction;
import com.ifrs.financeapp.model.transaction.TransactionType;

public record TransactionResponseDTO(
        Long id,
        BigDecimal amount,
        LocalDateTime createdAt,
        LocalDate transactionDate,
        TransactionType transactionType,
        RecurrenceType recurrenceType,
        CurrencyType currency,
        String description,
        String userLogin,
        CategoryDTO category,
        FixedRecurrencePeriodType fixedRecurrencePeriodType,
        Integer recurrenceDayOfMonth,
        LocalDate recurrenceEndDate) {

    public TransactionResponseDTO(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getCreatedAt(),
                transaction.getTransactionDate(),
                transaction.getTransactionType(),
                transaction.getRecurrenceType(),
                transaction.getCurrency(),
                transaction.getDescription(),
                transaction.getUser().getLogin(),
                new CategoryDTO(transaction.getCategory()),
                transaction.getFixedRecurrencePeriodType(),
                transaction.getRecurrenceDayOfMonth(),
                transaction.getRecurrenceEndDate());
    }
}
