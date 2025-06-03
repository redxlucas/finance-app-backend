package com.ifrs.financeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ifrs.financeapp.model.transaction.Expense;
import com.ifrs.financeapp.repository.ExpenseRepository;
import com.ifrs.financeapp.service.ExpenseService;

public class ExpenseServiceTest {
    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private Expense expenseMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveExpense() {
        final Long EXPENSE_ID = 1L;

        when(expenseMock.getId()).thenReturn(EXPENSE_ID);
        when(expenseRepository.save(expenseMock)).thenReturn(expenseMock);
    
        Expense saved = expenseService.save(expenseMock);
    
        assertNotNull(saved);
        assertEquals(EXPENSE_ID, saved.getId());
        verify(expenseRepository).save(expenseMock);
    }
}
