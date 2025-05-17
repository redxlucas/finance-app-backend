package com.ifrs.financeapp.dto;

import com.ifrs.financeapp.model.Category;

public record CategoryDTO(
    Long id,
    String name,
    String color
) {
    public CategoryDTO(Category category) {
        this(category.getId(), category.getName(), category.getColor());
    }
}
