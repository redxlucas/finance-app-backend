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

import com.ifrs.financeapp.model.transaction.Transaction;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.model.user.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByUser(User user, Pageable pageable);

    @Query("SELECT SUM(e.amount) FROM transactions e WHERE LOWER(e.category.name) = LOWER(:categoryName)")
    BigDecimal getTotalAmountByCategory(@Param("categoryName") String categoryName);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM transactions t WHERE t.transactionType = :type AND YEAR(t.transactionDate) = :#{#month.year} AND MONTH(t.transactionDate) = :#{#month.monthValue} AND t.user.id = :userId")
    BigDecimal sumByTypeAndMonth(@Param("type") TransactionType type, @Param("month") YearMonth month,
            @Param("userId") Long userId);

    @Query("SELECT t FROM transactions t WHERE t.transactionType = :type AND YEAR(t.transactionDate) = :#{#month.year} AND MONTH(t.transactionDate) = :#{#month.monthValue} AND t.user.id = :userId")
    List<Transaction> findByTypeAndMonth(@Param("type") TransactionType type, @Param("month") YearMonth month,
            @Param("userId") Long userId);

    @Query("""
                SELECT COALESCE(SUM(t.amount), 0)
                FROM transactions t
                WHERE t.transactionType = :type
                  AND t.user.id = :userId
                  AND t.transactionDate BETWEEN :startDate AND :endDate
            """)
    BigDecimal sumByTypeAndUserAndDateRange(
            @Param("type") TransactionType type,
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
