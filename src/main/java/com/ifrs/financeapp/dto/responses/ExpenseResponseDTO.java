package com.ifrs.financeapp.dto.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ifrs.financeapp.dto.CategoryDTO;
import com.ifrs.financeapp.model.Expense;

public record ExpenseResponseDTO(
    Long id,
    BigDecimal amount,
    LocalDateTime date,
    String description,
    List<CategoryDTO> categories
) {
    public ExpenseResponseDTO(Expense expense) {
        this(
            expense.getId(),
            expense.getAmount(),
            expense.getDate(),
            expense.getDescription(),
            expense.getCategoryList().stream()
                .map(CategoryDTO::new)
                .toList()
        );
    }
}
