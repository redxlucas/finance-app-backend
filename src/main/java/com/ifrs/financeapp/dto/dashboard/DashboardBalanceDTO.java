package com.ifrs.financeapp.dto.dashboard;

import java.math.BigDecimal;

public record DashboardBalanceDTO(
        BigDecimal currentBalance,
        BigDecimal previousBalance,
        double variationPercentage) {
}
