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

import com.ifrs.financeapp.model.transaction.Income;
import com.ifrs.financeapp.repository.IncomeRepository;
import com.ifrs.financeapp.service.IncomeService;

public class IncomeServiceTest {
    @InjectMocks
    private IncomeService incomeService;

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private Income incomeMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveIncome() {
        final Long EXPENSE_ID = 1L;

        when(incomeMock.getId()).thenReturn(EXPENSE_ID);
        when(incomeRepository.save(incomeMock)).thenReturn(incomeMock);
    
        Income saved = incomeService.save(incomeMock);
    
        assertNotNull(saved);
        assertEquals(EXPENSE_ID, saved.getId());
        verify(incomeRepository).save(incomeMock);
    }
}
