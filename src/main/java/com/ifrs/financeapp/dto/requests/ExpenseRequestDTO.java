package com.ifrs.financeapp.dto.requests;

import java.math.BigDecimal;
import java.util.List;

public record ExpenseRequestDTO(
    BigDecimal amount,
    String description,
    List<Long> categoryIds
) {
    public ExpenseRequestDTO {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (categoryIds == null) {
            categoryIds = List.of();
        }
    }
}
