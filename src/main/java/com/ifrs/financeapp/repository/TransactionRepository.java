package com.ifrs.financeapp.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.dto.dashboard.DashboardTopCategoryInterface;
import com.ifrs.financeapp.model.transaction.Transaction;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.model.user.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByUser(User user, Pageable pageable);

    @Query("""
                SELECT COALESCE(SUM(e.amount), 0)
                FROM transactions e
                WHERE LOWER(e.category.name) = LOWER(:categoryName)
                  AND e.user.id = :userId
                  AND YEAR(e.transactionDate) = :#{#month.year}
                  AND MONTH(e.transactionDate) = :#{#month.monthValue}
            """)
    BigDecimal getTotalAmountByCategoryAndMonth(
            @Param("categoryName") String categoryName,
            @Param("userId") Long userId,
            @Param("month") YearMonth month);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM transactions t WHERE t.transactionType = :type AND YEAR(t.transactionDate) = :#{#month.year} AND MONTH(t.transactionDate) = :#{#month.monthValue} AND t.user.id = :userId")
    BigDecimal sumByTypeAndMonth(@Param("type") TransactionType type, @Param("month") YearMonth month,
            @Param("userId") Long userId);

    @Query("SELECT t FROM transactions t WHERE t.transactionType = :type AND YEAR(t.transactionDate) = :#{#month.year} AND MONTH(t.transactionDate) = :#{#month.monthValue} AND t.user.id = :userId")
    List<Transaction> findByTypeAndMonth(@Param("type") TransactionType type, @Param("month") YearMonth month,
            @Param("userId") Long userId);

    @Query("""
                SELECT c.name AS categoryName, SUM(t.amount) AS total
                FROM transactions t
                JOIN t.category c
                WHERE t.transactionType = 'EXPENSE'
                  AND t.user.id = :userId
                  AND YEAR(t.transactionDate) = :#{#month.year}
                  AND MONTH(t.transactionDate) = :#{#month.monthValue}
                GROUP BY c.name
                ORDER BY total DESC
            """)
    List<DashboardTopCategoryInterface> findTopExpenseCategoryByMonth(
            @Param("month") YearMonth month,
            @Param("userId") Long userId,
            Pageable pageable);

    @Query("""
                SELECT COALESCE(SUM(CASE
                    WHEN t.transactionType = 'INCOME' THEN t.amount
                    WHEN t.transactionType = 'EXPENSE' THEN -t.amount
                    ELSE 0
                END), 0)
                FROM transactions t
                WHERE t.user.id = :userId
                AND t.transactionDate < :date
            """)
    BigDecimal getUserBalanceUntilDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    // @Query("""
    // SELECT new com.ifrs.financeapp.dto.DashboardChartDTO
    // t.transactionDate,
    // SUM(CASE WHEN t.transactionType = 'EXPENSE' THEN t.amount ELSE 0 END),
    // SUM(CASE WHEN t.transactionType = 'INCOME' THEN t.amount ELSE 0 END)
    // )
    // FROM Transaction t
    // WHERE t.user.id = :userId AND t.transactionDate BETWEEN :startDate AND
    // :endDate
    // GROUP BY t.transactionDate
    // ORDER BY t.transactionDate
    // """)
    // List<DashboardChartDTO> getDailyTotals(Long userId, LocalDate startDate,
    // LocalDate endDate);

    @Query(value = """
                SELECT t.transaction_date as date,
                       SUM(CASE WHEN t.transaction_type = 'EXPENSE' THEN t.amount ELSE 0 END) as expense,
                       SUM(CASE WHEN t.transaction_type = 'INCOME' THEN t.amount ELSE 0 END) as income
                FROM transactions t
                WHERE t.user_id = :userId AND t.transaction_date BETWEEN :startDate AND :endDate
                GROUP BY t.transaction_date
                ORDER BY t.transaction_date
            """, nativeQuery = true)
    List<Object[]> getMonthlyTotals(Long userId, LocalDate startDate, LocalDate endDate);

}
