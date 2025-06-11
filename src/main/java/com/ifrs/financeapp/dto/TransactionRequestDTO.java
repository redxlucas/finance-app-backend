package com.ifrs.financeapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ifrs.financeapp.model.transaction.CurrencyType;
import com.ifrs.financeapp.model.transaction.FixedRecurrencePeriodType;
import com.ifrs.financeapp.model.transaction.RecurrenceType;
import com.ifrs.financeapp.model.transaction.TransactionType;

public record TransactionRequestDTO(
        BigDecimal amount,
        LocalDate transactionDate,
        String description,
        TransactionType transactionType,
        CurrencyType currency,
        RecurrenceType recurrenceType,
        Long categoryId,

        FixedRecurrencePeriodType fixedRecurrencePeriodType,
        Integer recurrenceDayOfMonth,
        LocalDate recurrenceEndDate) {
    public TransactionRequestDTO {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor (amount) deve ser positivo e não nulo.");
        }

        if (transactionDate == null) {
            throw new IllegalArgumentException("A data da transação (transactionDate) não pode ser nula.");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("A descrição (description) não pode ser vazia.");
        }

        if (transactionType == null) {
            throw new IllegalArgumentException("O tipo da transação (transactionType) não pode ser nulo.");
        }

        if (currency == null) {
            throw new IllegalArgumentException("A moeda (currency) não pode ser nula.");
        }

        if (recurrenceType == null) {
            throw new IllegalArgumentException("O tipo de recorrência (recurrenceType) não pode ser nulo.");
        }

        if (categoryId == null) {
            throw new IllegalArgumentException("O ID da categoria (categoryId) não pode ser nulo.");
        }

        if (recurrenceType == RecurrenceType.FIXED) {
            if (fixedRecurrencePeriodType == null) {
                throw new IllegalArgumentException(
                        "Para transações fixas, o período da recorrência (fixedRecurrencePeriodType) deve ser especificado.");
            }
            if (recurrenceDayOfMonth != null && (recurrenceDayOfMonth < 1 || recurrenceDayOfMonth > 31)) {
                throw new IllegalArgumentException(
                        "O dia do mês da recorrência da transação (recurrenceDayOfMonth) deve ser entre 1 e 31.");
            }
            if (recurrenceEndDate != null && recurrenceEndDate.isBefore(transactionDate)) {
                throw new IllegalArgumentException(
                        "A data final da recorrência da transação (recurrenceEndDate) não pode ser anterior à data da transação (transactionDate).");
            }
        } else {
            if (fixedRecurrencePeriodType != null || recurrenceDayOfMonth != null || recurrenceEndDate != null) {
                throw new IllegalArgumentException(
                        "Campos de detalhamento de recorrência fixa (fixedRecurrencePeriodType, recurrenceDayOfMonth, recurrenceEndDate) só são permitidos para transações fixas.");
            }
        }
    }
}