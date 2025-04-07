package com.ifrs.financeapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.model.Expense;
import com.ifrs.financeapp.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Optional<Expense> getById(Long id) {
        return expenseRepository.findById(id);
    }

    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }
}
