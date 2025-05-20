package com.ifrs.financeapp.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.financeapp.dto.requests.ExpenseRequestDTO;
import com.ifrs.financeapp.dto.responses.ExpenseResponseDTO;
import com.ifrs.financeapp.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody ExpenseRequestDTO expenseDTO) {
        ExpenseResponseDTO savedExpense = expenseService.save(expenseDTO);
        return ResponseEntity.status(201).body(savedExpense);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id) {
        ExpenseResponseDTO expense = expenseService.getById(id);
        return ResponseEntity.ok().body(expense);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {
        List<ExpenseResponseDTO> listExpenses = expenseService.getAll();
        return ResponseEntity.ok().body(listExpenses);
    }

    @GetMapping("/total/category/{category}")
    public ResponseEntity<BigDecimal> getTotalAmountByCategory(@PathVariable String category) {
        BigDecimal totalExpenses = expenseService.getTotalAmountByCategory(category);
        return ResponseEntity.ok().body(totalExpenses);
    }
}
