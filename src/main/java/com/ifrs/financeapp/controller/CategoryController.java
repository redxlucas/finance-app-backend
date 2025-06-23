package com.ifrs.financeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.financeapp.dto.CategoryDTO;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAll());
    }

    @GetMapping("/expense")
    public ResponseEntity<List<CategoryDTO>> getExpenseCategories() {
        return ResponseEntity.ok().body(categoryService.getAllByType(TransactionType.EXPENSE));
    }

    @GetMapping("/income")
    public ResponseEntity<List<CategoryDTO>> getIncomeCategories() {
        return ResponseEntity.ok().body(categoryService.getAllByType(TransactionType.INCOME));
    }

}
