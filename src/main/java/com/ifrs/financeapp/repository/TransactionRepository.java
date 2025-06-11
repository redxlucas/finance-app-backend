package com.ifrs.financeapp.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.model.transaction.Transaction;
import com.ifrs.financeapp.model.user.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findAllByUser(User user, Pageable pageable);

    @Query("SELECT SUM(e.amount) FROM transactions e WHERE LOWER(e.category.name) = LOWER(:categoryName)")
    BigDecimal getTotalAmountByCategory(@Param("categoryName") String categoryName);
}
