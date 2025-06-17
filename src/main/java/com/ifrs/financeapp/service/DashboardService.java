package com.ifrs.financeapp.service;

import java.math.BigDecimal;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.dto.dashboard.DashboardBalanceDTO;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.repository.TransactionRepository;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public DashboardBalanceDTO getCurrentBalance(Long userId) {
        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        BigDecimal incomeCurrent = transactionRepository.sumByTypeAndMonth(TransactionType.INCOME, currentMonth,
                userId);
        BigDecimal expenseCurrent = transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, currentMonth,
                userId);
        BigDecimal balanceCurrent = incomeCurrent.subtract(expenseCurrent);

        BigDecimal incomePrevious = transactionRepository.sumByTypeAndMonth(TransactionType.INCOME, previousMonth,
                userId);
        BigDecimal expensePrevious = transactionRepository.sumByTypeAndMonth(TransactionType.EXPENSE, previousMonth,
                userId);
        BigDecimal balancePrevious = incomePrevious.subtract(expensePrevious);

        double variation = calculateVariation(balancePrevious, balanceCurrent);

        return new DashboardBalanceDTO(balanceCurrent, balancePrevious, variation);

    }

    private double calculateVariation(BigDecimal previous, BigDecimal current) {
        if (previous.compareTo(BigDecimal.ZERO) == 0)
            return 100.0;
        return current.subtract(previous)
                .divide(previous, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }
}
