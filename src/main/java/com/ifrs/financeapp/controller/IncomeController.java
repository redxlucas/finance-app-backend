package com.ifrs.financeapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.financeapp.model.transaction.Income;
import com.ifrs.financeapp.service.IncomeService;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> createIncome(@RequestBody Income income) {
        Income savedIncome = incomeService.save(income);
        return ResponseEntity.status(201).body(savedIncome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Income>> getIncomeById(@PathVariable Long id) {
        Optional<Income> optionalIncome = incomeService.getById(id);
        return ResponseEntity.ok().body(optionalIncome);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> listIncomes = incomeService.getAll();
        return ResponseEntity.ok().body(listIncomes);
    }
}
