package com.ifrs.financeapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.dto.requests.ExpenseRequestDTO;
import com.ifrs.financeapp.dto.responses.ExpenseResponseDTO;
import com.ifrs.financeapp.model.Category;
import com.ifrs.financeapp.model.Expense;
import com.ifrs.financeapp.repository.CategoryRepository;
import com.ifrs.financeapp.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ExpenseResponseDTO save(ExpenseRequestDTO expenseDTO) {

        List<Category> categories = expenseDTO.categoryIds().stream()
                .map(id -> categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + id)))
                .toList();
    
        Expense expense = new Expense();
        expense.setAmount(expenseDTO.amount());
        expense.setDescription(expenseDTO.description());
        expense.setCategoryList(categories);

        expenseRepository.save(expense);

        return new ExpenseResponseDTO(expense);
    }

    public Optional<Expense> getById(Long id) {
        return expenseRepository.findById(id);
    }

    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    // public Double getTotalByCategory(String category) {
    // return expenseRepository.getTotalAmountByCategory(category);
    // }
}
