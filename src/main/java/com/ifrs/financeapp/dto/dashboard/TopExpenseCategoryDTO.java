package com.ifrs.financeapp.dto.dashboard;

import java.math.BigDecimal;

public record TopExpenseCategoryDTO(
        String category,
        BigDecimal currentMonthAmount,
        BigDecimal previousMonthAmount,
        double variationPercentage) {
}
