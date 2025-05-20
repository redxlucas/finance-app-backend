package com.ifrs.financeapp.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.model.transaction.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE LOWER(e.category.name) = LOWER(:categoryName)")
    BigDecimal getTotalAmountByCategory(@Param("categoryName") String categoryName);
}
