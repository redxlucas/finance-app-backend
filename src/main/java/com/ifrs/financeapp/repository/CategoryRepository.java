package com.ifrs.financeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.model.category.Category;
import com.ifrs.financeapp.model.transaction.TransactionType;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(TransactionType type);
}
