package com.ifrs.financeapp.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DashboardChartDTO(LocalDate date, BigDecimal expense, BigDecimal income, BigDecimal total) {
}