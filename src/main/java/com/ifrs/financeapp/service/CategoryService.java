package com.ifrs.financeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.dto.CategoryDTO;
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

}
