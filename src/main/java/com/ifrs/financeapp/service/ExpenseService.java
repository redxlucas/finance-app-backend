package com.ifrs.financeapp.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.dto.requests.ExpenseRequestDTO;
import com.ifrs.financeapp.dto.responses.ExpenseResponseDTO;
import com.ifrs.financeapp.model.category.Category;
import com.ifrs.financeapp.model.transaction.Expense;
import com.ifrs.financeapp.repository.CategoryRepository;
import com.ifrs.financeapp.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ExpenseResponseDTO save(ExpenseRequestDTO expenseDTO) {

        Category category = categoryRepository.findById(expenseDTO.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + expenseDTO.categoryId()));

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.amount());
        expense.setType(expenseDTO.type());
        expense.setDescription(expenseDTO.description());
        expense.setCategory(category);

        expenseRepository.save(expense);

        return new ExpenseResponseDTO(expense);
    }

    public ExpenseResponseDTO getById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada: " + id));

        return new ExpenseResponseDTO(expense);
    }

    public List<ExpenseResponseDTO> getAll() {
        return expenseRepository.findAll().stream()
                .map(ExpenseResponseDTO::new)
                .toList();
    }

    public BigDecimal getTotalAmountByCategory(String category) {
        String formattedCategory = category.trim().toLowerCase();
        return expenseRepository.getTotalAmountByCategory(formattedCategory);
    }
}
