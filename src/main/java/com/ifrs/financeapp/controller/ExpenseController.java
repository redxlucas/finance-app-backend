package com.ifrs.financeapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.financeapp.model.Expense;
import com.ifrs.financeapp.service.ExpenseService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense savedExpense = expenseService.save(expense);
        return ResponseEntity.status(201).body(savedExpense);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Expense>> getExpenseById(@PathVariable Long id) {
        Optional<Expense> optionalExpense = expenseService.getById(id);
        return ResponseEntity.ok().body(optionalExpense);
    }
}
