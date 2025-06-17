package com.ifrs.financeapp.dto.dashboard;

import java.math.BigDecimal;

public record DashboardExpenseDTO(BigDecimal currentMonthTotal, BigDecimal previousMonthTotal,
        double variationPercentage) {
}
