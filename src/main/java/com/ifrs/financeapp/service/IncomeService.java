package com.ifrs.financeapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifrs.financeapp.model.transaction.Income;
import com.ifrs.financeapp.repository.IncomeRepository;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    public Optional<Income> getById(Long id) {
        return incomeRepository.findById(id);
    }

    public List<Income> getAll() {
        return incomeRepository.findAll();
    }
}
