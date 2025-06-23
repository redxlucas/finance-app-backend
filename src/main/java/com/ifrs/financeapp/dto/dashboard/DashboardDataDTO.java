package com.ifrs.financeapp.dto.dashboard;

import java.util.List;

public record DashboardDataDTO(
        DashboardBalanceDTO balance,
        DashboardExpenseDTO expense,
        DashboardCategoriesDTO category,
        List<DashboardChartDTO> chart) {

}
