package com.ifrs.financeapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.dto.dashboard.*;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.repository.TransactionRepository;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public DashboardBalanceDTO getCurrentBalance(Long userId) {
        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        BigDecimal incomeCurrent = defaultIfNull(
                transactionRepository.sumByTypeAndMonth(TransactionType.INCOME, currentMonth, userId));
        BigDecimal expenseCurrent = defaultIfNull(
                transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, currentMonth, userId));
        BigDecimal balanceCurrent = incomeCurrent.subtract(expenseCurrent);

        BigDecimal incomePrevious = defaultIfNull(
                transactionRepository.sumByTypeAndMonth(TransactionType.INCOME, previousMonth, userId));
        BigDecimal expensePrevious = defaultIfNull(
                transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, previousMonth, userId));
        BigDecimal balancePrevious = incomePrevious.subtract(expensePrevious);

        double variation = calculateVariation(balancePrevious, balanceCurrent);

        return new DashboardBalanceDTO(balanceCurrent, balancePrevious, variation);
    }

    public DashboardExpenseDTO getTotalExpense(Long userId) {
        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        BigDecimal expenseCurrent = defaultIfNull(
                transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, currentMonth, userId));
        BigDecimal expensePrevious = defaultIfNull(
                transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, previousMonth, userId));

        double variation = calculateVariation(expensePrevious, expenseCurrent);

        return new DashboardExpenseDTO(expenseCurrent, expensePrevious, variation);
    }

    public DashboardCategoriesDTO findTopExpenseCategoryByMonth(Long userId) {
        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        DashboardTopCategoryDTO currentTopCategory = getTopCategory(userId, currentMonth);

        if (currentTopCategory == null) {
            return new DashboardCategoriesDTO(null, BigDecimal.ZERO, 0);
        }

        BigDecimal previousTopCategory = defaultIfNull(
                transactionRepository.getTotalAmountByCategoryAndMonth(currentTopCategory.name(), userId,
                        previousMonth));

        double variation = calculateVariation(previousTopCategory, currentTopCategory.total());

        return new DashboardCategoriesDTO(currentTopCategory, previousTopCategory, variation);
    }

    public List<DashboardChartDTO> getDataChartMonth(Long userId) {
        YearMonth currentMonth = YearMonth.now();

        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        BigDecimal initialBalance = defaultIfNull(
                transactionRepository.getUserBalanceUntilDate(userId, startOfMonth.minusDays(1)));

        List<Object[]> results = transactionRepository.getMonthlyTotals(userId, startOfMonth, endOfMonth);

        if (results.isEmpty()) {
            return List.of();
        }

        results.sort((a, b) -> {
            LocalDate dateA = ((java.sql.Date) a[0]).toLocalDate();
            LocalDate dateB = ((java.sql.Date) b[0]).toLocalDate();
            return dateA.compareTo(dateB);
        });

        BigDecimal runningBalance = initialBalance;
        List<DashboardChartDTO> data = new ArrayList<>();

        for (Object[] r : results) {
            LocalDate date = extractDate(r[0]);

            BigDecimal expense = r[1] == null ? BigDecimal.ZERO : new BigDecimal(r[1].toString());
            BigDecimal income = r[2] == null ? BigDecimal.ZERO : new BigDecimal(r[2].toString());

            runningBalance = runningBalance.add(income).subtract(expense);
            data.add(new DashboardChartDTO(date, expense, income, runningBalance));
        }

        return data;
    }

    private DashboardTopCategoryDTO getTopCategory(Long userId, YearMonth month) {
        List<DashboardTopCategoryInterface> results = transactionRepository.findTopExpenseCategoryByMonth(
                month, userId, PageRequest.of(0, 1));

        if (results.isEmpty()) {
            return null;
        }

        DashboardTopCategoryInterface result = results.get(0);
        return new DashboardTopCategoryDTO(result.getCategoryName(), result.getTotal());
    }

    private double calculateVariation(BigDecimal previous, BigDecimal current) {
        if (previous.compareTo(BigDecimal.ZERO) == 0)
            return 100.0;

        return current.subtract(previous)
                .divide(previous, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }

    private BigDecimal defaultIfNull(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private LocalDate extractDate(Object dateObj) {
        if (dateObj instanceof java.sql.Date sqlDate) {
            return sqlDate.toLocalDate();
        } else if (dateObj instanceof java.util.Date utilDate) {
            return utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            throw new IllegalArgumentException("Data inválida no resultado da query");
        }
    }
}
