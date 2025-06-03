package com.ifrs.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.model.transaction.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}
