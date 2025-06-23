package com.ifrs.financeapp.dto;

import com.ifrs.financeapp.model.category.Category;
import com.ifrs.financeapp.model.transaction.TransactionType;

public record CategoryDTO(
        Long id,
        String name,
        String color,
        TransactionType type) {
    public CategoryDTO(Category category) {
        this(category.getId(), category.getName(), category.getColor(), category.getType());
    }
}
