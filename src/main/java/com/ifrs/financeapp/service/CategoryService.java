package com.ifrs.financeapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.dto.CategoryDTO;
import com.ifrs.financeapp.model.category.Category;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::new)
                .toList();
    }

    public List<CategoryDTO> getAllByType(TransactionType type) {
        List<Category> categories = categoryRepository.findByType(type);
        return categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

}
