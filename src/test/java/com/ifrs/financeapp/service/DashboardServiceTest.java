package com.ifrs.financeapp.service;

import com.ifrs.financeapp.dto.dashboard.*;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.repository.TransactionRepository;
import com.ifrs.financeapp.service.DashboardService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DashboardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentBalance() {
        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        when(transactionRepository.sumByTypeAndMonth(TransactionType.INCOME, currentMonth, userId))
                .thenReturn(BigDecimal.valueOf(3000));
        when(transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, currentMonth, userId))
                .thenReturn(BigDecimal.valueOf(1500));
        when(transactionRepository.sumByTypeAndMonth(TransactionType.INCOME, previousMonth, userId))
                .thenReturn(BigDecimal.valueOf(2500));
        when(transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, previousMonth, userId))
                .thenReturn(BigDecimal.valueOf(1000));

        DashboardBalanceDTO result = dashboardService.getCurrentBalance(userId);

        assertEquals(BigDecimal.valueOf(1500), result.currentBalance());
        assertEquals(BigDecimal.valueOf(1500), result.previousBalance());
        assertEquals(0.0, result.variationPercentage());
    }

    @Test
    void testGetTotalExpense() {
        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        when(transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, currentMonth, userId))
                .thenReturn(BigDecimal.valueOf(800));
        when(transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, previousMonth, userId))
                .thenReturn(BigDecimal.valueOf(400));

        DashboardExpenseDTO result = dashboardService.getTotalExpense(userId);

        assertEquals(BigDecimal.valueOf(800), result.currentMonthTotal());
        assertEquals(BigDecimal.valueOf(400), result.previousMonthTotal());
        assertEquals(100.0, result.variationPercentage());
    }

    @Test
    void testFindTopExpenseCategoryByMonth_noResult() {
        when(transactionRepository.findTopExpenseCategoryByMonth(any(), eq(userId), any()))
                .thenReturn(List.of());

        DashboardCategoriesDTO result = dashboardService.findTopExpenseCategoryByMonth(userId);

        assertNull(result.topCategory());
        assertEquals(BigDecimal.ZERO, result.previousTopValue());
        assertEquals(0.0, result.variationPercentage());
    }

    @Test
    void testGetDataChartMonth() {
        LocalDate start = YearMonth.now().atDay(1);
        LocalDate end = YearMonth.now().atEndOfMonth();

        when(transactionRepository.getUserBalanceUntilDate(userId, start.minusDays(1)))
                .thenReturn(BigDecimal.valueOf(1000));

        when(transactionRepository.getMonthlyTotals(eq(userId), eq(start), eq(end)))
                .thenReturn(new ArrayList<>(List.of(
                        new Object[] { java.sql.Date.valueOf(start), BigDecimal.valueOf(200), BigDecimal.valueOf(500) },
                        new Object[] { java.sql.Date.valueOf(start.plusDays(1)), BigDecimal.valueOf(300),
                                BigDecimal.valueOf(100) })));

        List<DashboardChartDTO> result = dashboardService.getDataChartMonth(userId);

        assertEquals(2, result.size());

        DashboardChartDTO first = result.get(0);
        assertEquals(start, first.date());
        assertEquals(BigDecimal.valueOf(200), first.expense());
        assertEquals(BigDecimal.valueOf(500), first.income());
        assertEquals(BigDecimal.valueOf(1300), first.total());

        DashboardChartDTO second = result.get(1);
        assertEquals(start.plusDays(1), second.date());
        assertEquals(BigDecimal.valueOf(300), second.expense());
        assertEquals(BigDecimal.valueOf(100), second.income());
        assertEquals(BigDecimal.valueOf(1100), second.total());
    }
}
