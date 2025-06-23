package com.ifrs.financeapp.service;

import com.ifrs.financeapp.dto.CategoryDTO;
import com.ifrs.financeapp.model.category.Category;
import com.ifrs.financeapp.model.transaction.TransactionType;
import com.ifrs.financeapp.repository.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Category> mockCategories = List.of(
                new Category(1L, "Food", "#FF0000", TransactionType.EXPENSE),
                new Category(2L, "Salary", "#00FF00", TransactionType.INCOME));

        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<CategoryDTO> result = categoryService.getAll();

        assertEquals(2, result.size());
        assertEquals("Food", result.get(0).name());
        assertEquals(TransactionType.EXPENSE, result.get(0).type());
        assertEquals("Salary", result.get(1).name());
        assertEquals(TransactionType.INCOME, result.get(1).type());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllByType() {
        TransactionType type = TransactionType.EXPENSE;

        List<Category> mockCategories = List.of(
                new Category(3L, "Transport", "#123456", type),
                new Category(4L, "Health", "#654321", type));

        when(categoryRepository.findByType(type)).thenReturn(mockCategories);

        List<CategoryDTO> result = categoryService.getAllByType(type);

        assertEquals(2, result.size());
        assertEquals("Transport", result.get(0).name());
        assertEquals("Health", result.get(1).name());

        verify(categoryRepository, times(1)).findByType(type);
    }
}
