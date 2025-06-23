package com.ifrs.financeapp.dto.dashboard;

import java.math.BigDecimal;

public record DashboardCategoriesDTO(DashboardTopCategoryDTO topCategory, BigDecimal previousTopValue,
        double variationPercentage) {

}
