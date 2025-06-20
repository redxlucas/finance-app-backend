package com.ifrs.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifrs.financeapp.model.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
