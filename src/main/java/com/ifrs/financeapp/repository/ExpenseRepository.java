package com.ifrs.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.category = :category")
    Double getTotalAmountByCategory(String category);
}
